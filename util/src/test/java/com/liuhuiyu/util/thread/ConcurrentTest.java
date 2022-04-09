package com.liuhuiyu.util.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-02-21 10:32
 */
public class ConcurrentTest {
    static int m = 0;

    @Test
    public void concurrentTest() {
        AtomicInteger integer = new AtomicInteger(0);
        new ConcurrentTest().begin(10, () -> {
            for (int i = 0; i < 10000; i++) {
                integer.addAndGet(1);
                m++;
            }
        }, () ->{
            System.out.println(m);
            System.out.println(integer.get());
        });
    }

    /**
     * 多线程测试用例
     *
     * @param threadCount 开启线程数
     * @param run         线程执行方法
     * @param finish      线程全部执行结束后运行方法
     */
    public void begin(int threadCount, Run run, Finish finish) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    countDownLatch.await();
                    run.run();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.countDown();
        fixedThreadPool.shutdown();
        while (!fixedThreadPool.isTerminated()) {
        }
        finish.finish();
    }

    public interface Run {
        void run();
    }

    public interface Finish {
        void finish();
    }
}
