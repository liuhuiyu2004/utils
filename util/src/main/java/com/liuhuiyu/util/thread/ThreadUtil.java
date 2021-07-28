package com.liuhuiyu.util.thread;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
    public static <T> void asynchronousDataLoading(@NotNull List<T> list, Consumer<T> consumer, Executor executor) {
        @SuppressWarnings({"unchecked", "hiding"})
        CompletableFuture<Void>[] list2 = new CompletableFuture[list.size()];
        for (int i = 0; i < list.size(); i++) {
            T t=list.get(i);
            list2[i] = CompletableFuture.runAsync(() -> consumer.accept(t), executor);
        }
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(list2);
        completableFuture.thenAccept((v) -> {
        });
        completableFuture.exceptionally((e) -> null);
        try {
            completableFuture.get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
