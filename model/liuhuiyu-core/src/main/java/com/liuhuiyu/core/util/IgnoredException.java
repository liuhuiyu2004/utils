package com.liuhuiyu.core.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 执行中出现异常，忽略不报错并且继续执行（多用于循环体中可忽略的循环执行）
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-05 9:53
 */
public class IgnoredException {
    public static void run(Runnable execution) {
        try {
            execution.run();
        }
        catch (Exception ignored) {
        }
    }

    public static void run(Runnable execution, Consumer<Exception> exceptionFunction) {
        try {
            execution.run();
        }
        catch (Exception ex) {
            exceptionFunction.accept(ex);
        }
    }

    public static <T> T get(Supplier<T> supplier, T def) {
        try {
            return supplier.get();
        }
        catch (Exception ex) {
            return def;
        }
    }
}
