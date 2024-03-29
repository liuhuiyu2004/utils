package com.liuhuiyu.util.thread;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-17 10:36
 */
public class ThreadUtilTest extends TestBase {

    @Test
    public void asynchronousDataLoading() {
        List<Integer> list = new ArrayList<>(5);
        Collections.addAll(list, 5, 1, 6, 4, 3, 2, 7, 5, 6);
        ThreadUtil.asynchronousDataLoading(list, this::testExe);
    }

    @Test
    public void asynchronousDataLoading2() {
        List<Integer> list = new ArrayList<>(5);
        Collections.addAll(list, 5, 1, 9, 4, 3, 2, 7, 8, 6);
        for (int i = 0; i < 100; i++) {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = ExecutorBuilder.create().corePoolSize(list.size()).threadName("test-" + i + "-").builder();
            ThreadUtil.asynchronousDataLoading(list, this::testExe, threadPoolTaskExecutor);
        }
    }

    private void testExe(Integer i) {
        try {
            LOG.info("开始：{}", i);
            Thread.sleep(i * 1000);
            LOG.info("完成：{}", i);
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
        //log.info("{},{}", list, list.size());
        //log.info(":{};{};", array, array.length);
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
//        //log.info("进入{}",i);
        //log.info("任务{}线程：", Thread.currentThread().getId());

        if (i < 3)
            throw new RuntimeException();
        try {
            Thread.sleep(i * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        //log.info("离开{}", Thread.currentThread().getId());
    }

    @Test
    public void testSleep() {
        Runnable runnable = () -> {
            long millis = 10_000;
            //log.info("开始执行:{}毫秒延时。", millis);
            if (ThreadUtil.sleep(millis)) {
                //log.info("正常结束。");
            }
            else {
                //log.info("线程内异常,中断休眠");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        //log.info("3秒后引发中断。");
        if (ThreadUtil.sleep(3_000)) {
            //log.info("引发中断。");
            thread.interrupt();
            //log.info("引发中断完成。");
        }
        long millis = 100_000;
        //log.info("程序完成。");
        if (!ThreadUtil.sleep(millis)) {
            //log.info("异常中断休眠");
        }
    }

    /**
     * 重入不影响已经传入的变量,但是会影响非原子变量
     * @author LiuHuiYu
     * Created DateTime 2021-09-09 16:37
     */
    @Test
    public void testThread() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        AtomicReference<Integer> atomicInt=new AtomicReference<>(0);
        for (int i1 = 0; i1 < 10; i1++) {
            atomicInt.set(i1);
            Integer i = i1;
            //log.info("开始{}",i);
            Thread thread = new Thread(() -> {
                //log.info("线程开始：{}",Thread.currentThread().getName());
                ThreadUtil.sleep(100);
                list.add(Thread.currentThread().getName()+":"+i+atomicInt.get());
                ss(i);
            });
            thread.setName("SetName" + i1);
            thread.start();
            //log.info("结束{}",i);
        }
        int j = 0;
        while (j < 10) {
            if (j != list.size()) {
                j = list.size();
            }
            ThreadUtil.sleep(1000);
        }
        //log.info("list={}", list);
    }

    private void ss(int i) {
        //log.info("id[{}]={}", Thread.currentThread().getName(), i);
    }
}