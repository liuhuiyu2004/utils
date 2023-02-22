package com.liuhuiyu.core.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-22 22:23
 */
public class ThreadPoolExecutorBuilder {

    /**
     * 参数初始化（初始化cpu数量）
     * Created DateTime 2021-07-17 10:31
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 初始化线程数量(2-4)
     * Created DateTime 2021-07-17 10:51
     */
    private int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    /**
     * 线程池大小(cpu * 2 +1)
     * Created DateTime 2021-07-17 10:51
     */
    private int maxPoolSize = CPU_COUNT * 2 + 1;

    /**
     * 线程空闲后的存活时长(30秒)
     * Created DateTime 2021-07-17 10:52
     */
    private int keepAliveSeconds = 30;
    private String threadName = "taskExecutor-";

    public static ThreadPoolExecutorBuilder create() {
        return new ThreadPoolExecutorBuilder();
    }

    /**
     * 核心线程数量大小
     *
     * @param corePoolSize 核心线程数量大小
     * @return com.liuhuiyu.util.thread.ThreadUtil.ExecutorBuilder
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 10:28
     */
    public ThreadPoolExecutorBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    /**
     * 线程池最大容纳线程数
     *
     * @param maxPoolSize 线程池最大容纳线程数
     * @return com.liuhuiyu.util.thread.ThreadUtil.ExecutorBuilder
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 10:28
     */
    public ThreadPoolExecutorBuilder maxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    /**
     * 线程空闲后的存活时长
     *
     * @param keepAliveSeconds 线程空闲后的存活时长
     * @return com.liuhuiyu.util.thread.ThreadUtil.ExecutorBuilder
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 10:27
     */
    public ThreadPoolExecutorBuilder keepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
        return this;
    }

    /**
     * 线程名称
     *
     * @param threadName 线程名称
     * @return com.liuhuiyu.util.thread.ThreadUtil.ExecutorBuilder
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 10:27
     */
    public ThreadPoolExecutorBuilder threadName(String threadName) {
        this.threadName = threadName;
        return this;
    }

    public ExecutorService builder() {
        EasyThreadFactory easyThreadFactory = new EasyThreadFactory(threadName);
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), easyThreadFactory);
    }
}
