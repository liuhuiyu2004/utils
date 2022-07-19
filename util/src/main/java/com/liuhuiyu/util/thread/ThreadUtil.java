package com.liuhuiyu.util.thread;

import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.concurrent.Executor;

/**
 * 多线程工具
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-04-14 14:02
 */
public class ThreadUtil {
    /**
     * 异步List循环(等待返回结果)
     *
     * @param list     list
     * @param consumer list处理
     * @author LiuHuiYu
     * Created DateTime 2021-04-14 14:03
     */
    public static <T> void asynchronousDataLoading(List<T> list, Consumer<T> consumer) {
        asynchronousDataLoading(list, consumer, ExecutorBuilder.create().threadName("async-load-").builder());
    }

    /**
     * 异步List循环(等待返回结果)
     *
     * @param list     list
     * @param consumer list处理
     * @param executor 线程池
     * @author LiuHuiYu
     * Created DateTime 2021-04-14 14:03
     */
    public static <T> void asynchronousDataLoading(@NotNull List<T> list, Consumer<T> consumer, ThreadPoolTaskExecutor executor) {
        asynchronousDataLoading(list, consumer, executor, executor::shutdown);
    }

    /**
     * 异步List循环(等待返回结果)
     *
     * @param list          list
     * @param consumer      list处理
     * @param executor      线程池
     * @param closeExecutor 提供关闭线程池的方法
     * @author LiuHuiYu
     * Created DateTime 2021-04-14 14:03
     */
    public static <T> void asynchronousDataLoading(@NotNull List<T> list, Consumer<T> consumer, Executor executor, Runnable closeExecutor) {
        CompletableFuture.allOf(list.stream()
                .map(item -> CompletableFuture.runAsync(() -> consumer.accept(item), executor))
                .toArray(CompletableFuture[]::new)).join();
        closeExecutor.run();
    }

    /**
     * 休眠（带返回状态(成功：正常无中断；失败：异常中断引发休眠结束)）
     *
     * @param millis 休眠时间
     * @author LiuHuiYu
     * Created DateTime 2021-08-20 14:43
     */
    @SuppressWarnings("All")
    public static boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
            return true;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            /*
如果不加上这一句，那么cd将会都是false，因为在捕捉到InterruptedException异常的时候就会自动的中断标志置为了false
            Boolean c = Thread.interrupted();
            Boolean d = Thread.interrupted();
            log.error("中断引发休眠异常:{};重置中断位{}->{}", e.getMessage(), c, d);
             */
            return false;
        }
    }

    public static void concurrent(){

    }
}
