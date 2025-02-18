package com.liuhuiyu.core.thread;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-10 9:36
 */
class ThreadUtilTest extends AbstractTest {
    @Test
    public void completableFuture() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture[] futures = new CompletableFuture[2];
        final Integer[] array = IntStream.range(1, 10).boxed().toArray(Integer[]::new);
        LOG.info("begin");
        futures[0] = CompletableFuture.runAsync(() -> {
            LOG.info("begin1:{}", array.length);
            if (array.length > 0) {
                throw new IllegalArgumentException("error");
            }
            ThreadUtil.sleep(4_000);
            LOG.info("end1");
        }, executorService).handle((result, e) -> {
            LOG.info("异常信息：{}", e);
            return null;
        });
        futures[1] = CompletableFuture.runAsync(() -> {
            LOG.info("begin2:{}", array.length);
            ThreadUtil.sleep(3_000);
            LOG.info("end2");
        }, executorService);
        CompletableFuture.allOf(futures).join();
        LOG.info("end");
    }
}