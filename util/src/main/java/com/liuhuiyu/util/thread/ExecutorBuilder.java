package com.liuhuiyu.util.thread;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-17 10:29
 */
public class ExecutorBuilder {
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
     * 阻塞队列(20)
     * Created DateTime 2021-07-17 10:52
     */
    private int workQueue = 20;
    /**
     * 线程空闲后的存活时长(30秒)
     * Created DateTime 2021-07-17 10:52
     */
    private int keepAliveSeconds = 30;
    private String threadName = "taskExecutor-";

    public static ExecutorBuilder create() {
        return new ExecutorBuilder();
    }

    /**
     * 核心线程数量大小
     *
     * @param corePoolSize 核心线程数量大小
     * @return com.liuhuiyu.util.thread.ThreadUtil.ExecutorBuilder
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 10:28
     */
    public ExecutorBuilder corePoolSize(int corePoolSize) {
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
    public ExecutorBuilder maxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    /**
     * 阻塞队列
     *
     * @param workQueue 阻塞队列
     * @return com.liuhuiyu.util.thread.ThreadUtil.ExecutorBuilder
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 10:28
     */
    public ExecutorBuilder workQueue(int workQueue) {
        this.workQueue = workQueue;
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
    public ExecutorBuilder keepAliveSeconds(int keepAliveSeconds) {
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
    public ExecutorBuilder threadName(String threadName) {
        this.threadName = threadName;
        return this;
    }

    public ThreadPoolTaskExecutor builder() {
        ThreadPoolExecutor   t;
//        return (r)->{
//            Thread thread=new Thread(r);
//            thread.setName(threadName);
//            thread.start();
//        };
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        //等待队列
        threadPoolTaskExecutor.setQueueCapacity(workQueue);
        //线程前缀
        threadPoolTaskExecutor.setThreadNamePrefix(threadName);
        //线程池维护线程所允许的空闲时间,单位为秒
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
