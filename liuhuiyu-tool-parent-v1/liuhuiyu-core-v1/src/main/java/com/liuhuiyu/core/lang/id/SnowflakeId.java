package com.liuhuiyu.core.lang.id;

import com.liuhuiyu.core.util.Assert;

import java.sql.Timestamp;

/**
 * 雪花算法生成Id（默认支持69年id生成）可通过自定义生成更广范围的id<p>
 * Created DateTime 2023-01-16 16:11
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 */
public class SnowflakeId {
    /**
     * 开始时间截 LocalDateTime.of(2023,1,1,0,0)
     */
    public static final long INITIALIZE_THE_TIMESTAMP = 1_672_502_400_000L;

    /**
     * 机器id所占的位数
     */
    public static final long WORKER_ID_BITS = 5L;

    /**
     * 数据标识id所占的位数
     */
    public static final long DATACENTER_ID_BITS = 5L;

    /**
     * 序列在id中占的位数
     */
    public static final long SEQUENCE_BITS = 12L;
    /**
     * 时间戳左移最大位数
     * Created DateTime 2023-01-17 10:07
     */
    public static final long MAX_TIMESTAMP_LEFT_SHIFT = 22L;
    private final long initializeTheTimestamp;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long datacenterIdShift;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift;

    /**
     * 生成序列的掩码，根据 sequenceBits 生成 (0b(sequenceBits)1)
     */
    private final long sequenceMask;

    /**
     * 工作机器ID(0~31)
     */
    private final long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private final long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeId(long workerId, long datacenterId) {
        this(INITIALIZE_THE_TIMESTAMP, WORKER_ID_BITS, DATACENTER_ID_BITS, SEQUENCE_BITS, workerId, datacenterId);
    }

    /**
     * 自定义初始化构造函数
     *
     * @param initializeTheTimestamp 初始化时间
     * @param workerIdBits           工作ID位数(bit)
     * @param datacenterIdBits       数据中心ID位数(bit)
     * @param sequenceBits           序列占用的位数(bit)
     * @param workerId               工作ID(0~2^workerIdBits-1)
     * @param datacenterId           数据中心ID(0~2^datacenterIdBits-1)
     * @author LiuHuiYu
     * Created DateTime 2023-01-17 9:30
     */
    public SnowflakeId(Timestamp initializeTheTimestamp, long workerIdBits, long datacenterIdBits, long sequenceBits, long workerId, long datacenterId) {
        this(initializeTheTimestamp.getTime(), workerIdBits, datacenterIdBits, sequenceBits, workerId, datacenterId);
    }

    /**
     * 自定义初始化构造函数
     * Created DateTime 2023-01-17 9:30
     *
     * @param initializeTheTimestamp 初始化时间戳
     * @param workerIdBits           工作ID位数(bit)
     * @param datacenterIdBits       数据中心ID位数(bit)
     * @param sequenceBits           序列占用的位数(bit)
     * @param workerId               工作ID(0~2^workerIdBits-1)
     * @param datacenterId           数据中心ID(0~2^datacenterIdBits-1)
     * @author LiuHuiYu
     */
    public SnowflakeId(long initializeTheTimestamp, long workerIdBits, long datacenterIdBits, long sequenceBits, long workerId, long datacenterId) {
        final long maxWorkerId = ~(-1L << workerIdBits);
        final long maxDatacenterId = ~(-1L << datacenterIdBits);
        Assert.assertTrue(initializeTheTimestamp >= 0, new IllegalArgumentException("初始化时间戳 不能小于 0"));
        Assert.assertTrue(workerIdBits > 0, new IllegalArgumentException("工作ID位数 不能小于 0"));
        Assert.assertTrue(datacenterIdBits > 0, new IllegalArgumentException("数据中心ID位数 %d 不能小于 0"));
        Assert.assertTrue(sequenceBits > 0, new IllegalArgumentException("序列占用的位数 %d 不能小于 0"));
        Assert.assertTrue(workerId <= maxWorkerId && workerId >= 0, new IllegalArgumentException(String.format("工作ID 不能大于 %d 不能小于 0", maxWorkerId)));
        Assert.assertTrue(datacenterId <= maxWorkerId && datacenterId >= 0, new IllegalArgumentException(String.format("数据中心ID 不能大于 %d 不能小于 0", maxDatacenterId)));
        this.initializeTheTimestamp = initializeTheTimestamp;
        this.sequenceMask = ~(-1L << sequenceBits);
        this.workerIdShift = sequenceBits;
        this.datacenterIdShift = sequenceBits + workerIdBits;
        this.timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        Assert.assertTrue(this.timestampLeftShift <= MAX_TIMESTAMP_LEFT_SHIFT, new IllegalArgumentException(String.format("工作ID位数(bit)+数据中心ID位数(bit)+序列占用的位数(bit) 不能大于 %d", MAX_TIMESTAMP_LEFT_SHIFT)));
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        Assert.assertTrue(timestamp >= lastTimestamp, new RuntimeException(String.format("时钟倒退。拒绝生成 id %d 毫秒", lastTimestamp - timestamp)));

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - initializeTheTimestamp) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
