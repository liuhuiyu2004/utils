package com.liuhuiyu.core.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @param <T> 输入
 * @param <R> 输出
 *            Created DateTime 2023-01-27 10:23
 * @author LiuHuiYu
 * @version v1.0.0.0
 */
public class IfRun<T, R> {
    private final T t;

    @SuppressWarnings("all")
    private Optional<R> resOptional;

    //region 初始化函数

    private IfRun(T t) {
        this.t = t;
    }

    public static <T, R> IfRun<T, R> create() {
        return new IfRun<>(null);
    }

    /**
     * 链式多条件执行并返回结果，如果都没有执行将返回默认
     *
     * @param t   输出参数
     * @param <T> 输出参数
     * @param <R> 输出参数
     * @return com.liuhuiyu.core.util.IfRun
     * @author LiuHuiYu
     * Created DateTime 2023-01-27 10:31
     */
    public static <T, R> IfRun<T, R> create(T t) {
        return new IfRun<>(t);
    }
    //endregion

    //region 条件执行

    public IfRun<T, R> elseIf(boolean b, Function<T, R> f) {
        if (isAllowExecution(b)) {
            this.resOptional = Optional.ofNullable(f.apply(t));
        }
        return this;
    }

    /**
     * 添加执行
     *
     * @param b        判断是否执行
     * @param supplier 要执行的函数
     * @return com.liuhuiyu.util.list.IfRun
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     */
    public IfRun<T, R> elseIf(Boolean b, Supplier<R> supplier) {
        if (this.isAllowExecution(b)) {
            this.resOptional = Optional.ofNullable(supplier.get());
        }
        return this;
    }

    public IfRun<T, R> elseIf(Boolean b, Runnable execution) {
        if (this.isAllowExecution(b)) {
            execution.run();
            this.resOptional = Optional.empty();
        }
        return this;
    }

    /**
     * 如果上面任何一个条件都不满足就返回指定值
     *
     * @param b 判断是否执行
     * @param r 赋予的值
     * @return com.liuhuiyu.core.util.IfRun
     * @author LiuHuiYu
     * Created DateTime 2023-01-27 11:28
     * @deprecated 此方法会在代码编写过程中因编码错误引发故障。
     */
    @Deprecated
    public IfRun<T, R> elseIf(Boolean b, R r) {
        if (this.isAllowExecution(b)) {
            this.resOptional = Optional.ofNullable(r);
        }
        return this;
    }
    //endregion

    //region 结果获取或者处理

    /**
     * 获取执行后的信息
     *
     * @return java.util.Optional
     * @author LiuHuiYu
     * Created DateTime 2023-01-27 11:11
     */
    public Optional<R> getOptionalResults() {
        return this.isAlreadyExecution() ? this.resOptional : Optional.empty();
    }

    /**
     * 没有返回结构将抛出异常
     *
     * @param exceptionSupplier 将返回要抛出的异常的供应商
     * @param <X> 将返回要抛出的异常的供应商
     * @return R
     * @author LiuHuiYu
     * Created DateTime 2022-06-06 16:51
     */
    public <X extends Throwable> R orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.isAlreadyExecution()) {
            return this.resOptional.orElse(null);
        }
        else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 如果上面任何一个条件都不满足就返回指定值
     *
     * @param r 指定值
     * @return R
     * @author LiuHuiYu
     * Created DateTime 2023-01-24 13:56
     */
    public R orElse(R r) {
        if (this.isAlreadyExecution()) {
            return this.resOptional.orElse(null);
        }
        else {
            return r;
        }
    }

    public R orElse(Function<T, R> f) {
        if (this.isAlreadyExecution()) {
            return this.resOptional.orElse(null);
        }
        else {
            return f.apply(t);
        }
    }

    /**
     * 添加执行
     *
     * @param supplier 要执行的函数
     * @return com.liuhuiyu.util.list.IfRun
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     */
    public R orElse(Supplier<R> supplier) {
        if (this.isAlreadyExecution()) {
            return this.resOptional.orElse(null);
        }
        else {
            return supplier.get();
        }
    }

    public void orElse(Runnable execution) {
        if (!this.isAlreadyExecution()) {
            execution.run();
        }
    }
    //endregion

    /**
     * 允许执行方法（之前没有任何可用的执行方法）
     *
     * @param b 执行条件
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-01-24 14:09
     */
    private boolean isAllowExecution(Boolean b) {
        return b && !isAlreadyExecution();
    }

    /**
     * 已经有执行的函数了
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-01-24 14:10
     */
    @SuppressWarnings("all")
    private boolean isAlreadyExecution() {
        return null != this.resOptional;
    }
}
