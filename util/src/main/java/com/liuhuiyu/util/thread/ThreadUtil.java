package com.liuhuiyu.util.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 多线程工具
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-04-14 14:02
 */
public class ThreadUtil {
    /**
     * 异步List循环
     *
     * @param list     list
     * @param consumer list处理
     * @author LiuHuiYu
     * Created DateTime 2021-04-14 14:03
     */
    public static <T> void asynchronousDataLoading(List<T> list, Consumer<T> consumer) {
        List<CompletableFuture<Void>> threadPool = new ArrayList<>();
        list.forEach((item) -> {
            CompletableFuture<Void> d = CompletableFuture.runAsync(() -> consumer.accept(item));
            threadPool.add(d);
        });
        threadPool.forEach((future) -> {
            try {
                future.get();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
