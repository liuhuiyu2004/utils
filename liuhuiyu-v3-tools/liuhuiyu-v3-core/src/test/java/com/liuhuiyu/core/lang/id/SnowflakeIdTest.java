package com.liuhuiyu.core.lang.id;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-16 16:11
 */
class SnowflakeIdTest extends AbstractTest {
    @Test
    public void create() {
        SnowflakeId idWorker = new SnowflakeId(0, 0);
        long[] ids = new long[40_000];
        for (int i = ids.length - 1; i >= 0; i--) {
            ids[i] = idWorker.nextId();
        }
        for (long id : ids) {
            LOG.info("{};{}", Long.toBinaryString(id), id);
        }
    }

    @Test
    public void create2() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0);
        Timestamp init = Timestamp.valueOf(localDateTime);
        SnowflakeId idWorker = new SnowflakeId(init, SnowflakeId.WORKER_ID_BITS, SnowflakeId.DATACENTER_ID_BITS, SnowflakeId.SEQUENCE_BITS, 0, 0);
        long[] ids = new long[40_000];
        for (int i = ids.length - 1; i >= 0; i--) {
            ids[i] = idWorker.nextId();
        }
        for (long id : ids) {
            LOG.info("{};{}", Long.toBinaryString(id), id);
        }
    }

    @Test
    public void maxDate() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0);
        Timestamp init = Timestamp.valueOf(localDateTime);
        Timestamp timestamp = new Timestamp(Long.parseLong("0", 2));
        Timestamp timestamp2 = new Timestamp(Long.parseLong("11111111111111111111111111111111111111111", 2));
        long l = timestamp.getTime();
        LOG.info("{};{};{}", Long.toBinaryString(init.getTime()), init.getTime(), init);
        LOG.info("{};{};{}", Long.toBinaryString(l), l, timestamp);
        LOG.info("{};{};{}", Long.toBinaryString(timestamp2.getTime()), timestamp2.getTime(), timestamp2);
        LOG.info("{};{}", Long.toBinaryString(Long.MAX_VALUE), Long.MAX_VALUE);
    }
}