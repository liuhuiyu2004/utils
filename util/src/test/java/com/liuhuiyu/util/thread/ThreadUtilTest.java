package com.liuhuiyu.util.thread;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-17 10:36
 */
@Log4j2
public class ThreadUtilTest {
    @Test
    public void asynchronousDataLoading() {
        List<Integer> list = new ArrayList<>(5);
        Collections.addAll(list, 5, 1, 6, 4, 3, 2, 7, 5, 6);
        ThreadUtil.asynchronousDataLoading(list, this::testExe);
    }

    @Test
    public void asynchronousDataLoading2() {
        List<Integer> list = new ArrayList<>(5);
        Collections.addAll(list, 5, 1, 6, 4, 3, 2, 7, 5, 6);
        for (int i = 0; i < 100; i++) {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = ExecutorBuilder.create().corePoolSize(list.size()).threadName("test-" + i + "-").builder();
            ThreadUtil.asynchronousDataLoading(list, this::testExe, threadPoolTaskExecutor);
        }
    }

    private void testExe(Integer i) {
        try {
            log.info("开始：{}", i);
            Thread.sleep(i * 1000);
            log.info("完成：{}", i);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void s() {
        List<String> list = new ArrayList<String>() {{
            this.add("1");
            this.add("2");
            this.add("3");
            this.add("3");
            this.add("32");
        }};
        String[] array = list.toArray(new String[0]);
        log.info("{},{}", list, list.size());
        log.info(":{};{};", array, array.length);
    }

    private List<Integer> getIntegerList() {
        return new ArrayList<Integer>() {{
            this.add(1);
            this.add(2);
            this.add(3);
            this.add(4);
            this.add(5);
        }};
    }

    @Test
    @SuppressWarnings({"rawtypes", "unused"})
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        List<Integer> integerList = getIntegerList();
//        AtomicReference<CompletableFuture<Integer>> future = new AtomicReference<>();
        integerList.forEach(i -> {
            CompletableFuture<Integer> future01 = CompletableFuture.supplyAsync(() -> {
                sleep(i);
                return i;
            });
//            future.set(future01);
        });
        CompletableFuture.supplyAsync(() -> integerList.stream().map((item) -> CompletableFuture.runAsync(() -> sleep(item))).collect(Collectors.toList()));
        CompletableFuture[] s2 = integerList.stream().map((item) -> CompletableFuture.runAsync(() -> sleep(item))).toArray(CompletableFuture[]::new);
        CompletableFuture.anyOf(s2).thenAccept((a) -> {
        }).get();
    }

    private void sleep(Integer i) {
//        log.info("进入{}",i);
        log.info("任务{}线程：", Thread.currentThread().getId());

        if (i < 3)
            throw new RuntimeException();
        try {
            Thread.sleep(i * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("离开{}", Thread.currentThread().getId());
    }

    @Test
    public void testSleep() {
        Runnable runnable = () -> {
            long millis = 10_000;
            log.info("开始执行:{}毫秒延时。", millis);
            if (ThreadUtil.sleep(millis)) {
                log.info("正常结束。");
            }
            else {
                log.info("线程内异常,中断休眠");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        log.info("3秒后引发中断。");
        if (ThreadUtil.sleep(3_000)) {
            log.info("引发中断。");
            thread.interrupt();
            log.info("引发中断完成。");
        }
        long millis = 100_000;
        log.info("程序完成。");
        if (!ThreadUtil.sleep(millis)) {
            log.info("异常中断休眠");
        }
    }
}