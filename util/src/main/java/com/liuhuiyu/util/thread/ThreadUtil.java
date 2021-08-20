package com.liuhuiyu.util.thread;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static <T> void asynchronousDataLoading(@NotNull List<T> list, Consumer<T> consumer, ThreadPoolTaskExecutor executor) {
        asynchronousDataLoading(list, consumer, executor, executor::shutdown);
    }

    /**
     * 异步List循环(等待返回结果)
     *
     * @param list     list
     * @param consumer list处理
     * @param executor 线程池
     * @param closeExecutor 提供关闭线程池的方法
     * @author LiuHuiYu
     * Created DateTime 2021-04-14 14:03
     */
    public static <T> void asynchronousDataLoading(@NotNull List<T> list, Consumer<T> consumer, Executor executor, Runnable closeExecutor) {
        CompletableFuture.allOf(list.stream()
                .map(item -> CompletableFuture.runAsync(() -> {
                    consumer.accept(item);
                }, executor)).toArray(CompletableFuture[]::new)).join();
        closeExecutor.run();
    }
}
