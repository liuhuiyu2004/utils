package com.liuhuiyu.util.thread;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * 多线程工具
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-04-14 14:02
 */
@Log4j2
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
        CompletableFuture<List<CompletableFuture<Void>>> completableFutureList = CompletableFuture.supplyAsync(() -> list.stream()
                .map(item -> CompletableFuture.runAsync(() -> consumer.accept(item), executor))
                .collect(Collectors.toList())
        );
        try {
            completableFutureList.thenAccept(completableFutures -> {
                CompletableFuture<Void> completableFuture = CompletableFuture
                        .allOf(completableFutures.toArray(new CompletableFuture[0]));
                try {
                    completableFuture.get();
                }
                catch (InterruptedException | ExecutionException e) {
                    log.error(e);
                }
            }).get();
        }
        catch (InterruptedException | ExecutionException e) {
            log.error(e);
        }
    }
}
