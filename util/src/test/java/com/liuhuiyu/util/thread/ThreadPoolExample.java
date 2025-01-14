package com.liuhuiyu.util.thread;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ThreadPoolExample extends TestBase {
    @Test
    public void test() {
        for (int v1 = 0; v1 < 10; v1++) {
            LOG.info("time{}", v1);
            for (int i1 = 0; i1 < 200_000; i1++) {
                for (int i2 = 0; i2 < 10_000; i2++) {
                    for (int i3=10; i3 > 0; i3--){

                    }
                }
            }
            LOG.info("time{}", v1);
        }
        Thread.sleep(1000);
        // 创建一个线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4, // 核心线程数
                10, // 最大线程数
                5L, // 空闲线程存活时间
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingQueue<>(10) // 任务队列
        );

        // 提交任务到线程池
        for (int i = 1; i < 50; i++) {
            int taskId = i;
            Future<?> future = executor.submit(() -> {
                LOG.info("Task {} started by {}", taskId, Thread.currentThread().getName());
                long v = 0;
                for (int i1 = 0; i1 < 10_000_000; i1++) {
                    for (int i2 = 0; i2 < 1_000; i2++) {
                        for (int i3 = taskId; i3 > 0; i3--) {
                            v++;
                        }
                    }
                }
                if (Thread.currentThread().isInterrupted()) {
                    LOG.error("Task " + taskId + " interrupted:{}", v);
                }
                else {
                    LOG.info("Task " + taskId + " completed {}", v);
                }
            });

            // 监控任务执行时间，如果超过指定时间则取消任务
            try {
                future.get(3, TimeUnit.SECONDS); // 设置超时时间为2秒
            }
            catch (TimeoutException e) {
                LOG.error("Task " + taskId + " timed out");
                future.cancel(true); // 取消任务
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 关闭线程池
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
        catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}