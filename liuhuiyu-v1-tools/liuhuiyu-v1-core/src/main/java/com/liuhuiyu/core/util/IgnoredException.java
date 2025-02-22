package com.liuhuiyu.core.util;

import com.liuhuiyu.core.lang.function_interface.RunnableException;
import com.liuhuiyu.core.lang.function_interface.SupplierException;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 执行中出现异常，忽略不报错并且继续执行（多用于循环体中可忽略的循环执行）
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-05 9:53
 */
public class IgnoredException {
    /**
     * 执行中出现异常，忽略不报错并且继续执行（多用于循环体中可忽略的循环执行）
     *
     * @param execution 要执行的 Runnable

     * Created DateTime 2023-02-22 21:37
     */
    public static void run(RunnableException execution) {
        try {
            execution.run();
        }
        catch (Exception ignored) {
        }
    }

    /**
     * 执行中出现异常，忽略不报错并且继续执行（多用于循环体中可忽略的循环执行）
     *
     * @param execution         要执行的 Runnable
     * @param exceptionFunction 异常出现要执行的 Consumer
     * Created DateTime 2023-02-22 21:37
     */
    public static void run(RunnableException execution, Consumer<Exception> exceptionFunction) {
        try {
            execution.run();
        }
        catch (Exception ex) {
            exceptionFunction.accept(ex);
        }
    }

    /**
     * 执行中出现异常，返回默认值
     *
     * @param supplier 要执行的 Supplier
     * @param def      默认值
     * @return T
     * @deprecated 使用 getOrElse 方法替换此功能
     * Created DateTime 2023-02-22 21:38
     */
    @Deprecated
    public static <T> T get(SupplierException<T> supplier, T def) {
        try {
            return supplier.get();
        }
        catch (Exception ex) {
            return def;
        }
    }

    /**
     * 执行中出现异常，返回默认值
     *
     * @param supplier          要执行的 Supplier
     * @param exceptionFunction 异常的时候执行
     * @return T
     * @deprecated 使用 getOrElseGet 方法替换此功能
     * Created DateTime 2023-02-22 21:38
     */
    @Deprecated
    public static <T> T get(SupplierException<T> supplier, Function<Exception, T> exceptionFunction) {
        try {
            return supplier.get();
        }
        catch (Exception ex) {
            return exceptionFunction.apply(ex);
        }
    }

    /**
     * 执行中出现异常，返回默认值
     *
     * @param supplier 要执行的 Supplier
     * @param def      默认值
     * @return T
     * Created DateTime 2023-02-22 21:38
     */
    public static <T> T getOrElse(SupplierException<T> supplier, T def) {
        try {
            return supplier.get();
        }
        catch (Exception ex) {
            return def;
        }
    }

    /**
     * 执行中出现异常，返回默认值
     *
     * @param supplier          要执行的 Supplier
     * @param exceptionFunction 异常的时候执行
     * @return T
     * Created DateTime 2023-02-22 21:38
     */
    public static <T> T getOrElseGet(SupplierException<T> supplier, Function<Exception, T> exceptionFunction) {
        try {
            return supplier.get();
        }
        catch (Exception ex) {
            return exceptionFunction.apply(ex);
        }
    }
}
