package com.liuhuiyu.core.help;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 执行功能（成功一次后后面的将不执行）<p>
 * Created on 2025/4/6 21:47
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class ExecutionFunction<T, R> {
    @SuppressWarnings("All")
    Optional<Optional<R>> optionalR = Optional.empty();
    T t;
    RuntimeException exception = null;

    public ExecutionFunction(T t) {
        this.t = t;
    }

    /**
     * 获取当前执行值
     *
     * @return java.util.Optional<R>
     */
    private Optional<R> getR() {
        return optionalR.orElse(null);
    }

    public static <T, R> ExecutionFunction<T, R> begin(T t) {
        return new ExecutionFunction<>(t);
    }

    public static <R> ExecutionFunction<Void, R> begin() {
        return new ExecutionFunction<>(null);
    }

    /**
     * 没有成功就执行
     *
     * @param function 执行函数
     * @return com.liuhuiyu.util.date.LocalDateUtil.ExecutionFunction&lt;T, R>
     */
    public ExecutionFunction<T, R> notSucceedExecution(Function<T, R> function) {
        if (!this.hasExecutedSuccess()) {
            try {
                R r = function.apply(t);
                this.optionalR = Optional.of(Optional.ofNullable(r));
            }
            catch (Exception ex) {
                this.exception = new RuntimeException(ex);
            }
        }
        return this;
    }

    /**
     * 没有成功就执行
     *
     * @param supplier 执行函数
     * @return com.liuhuiyu.util.date.LocalDateUtil.ExecutionFunction&lt;T, R>
     */
    public ExecutionFunction<T, R> notSucceedExecution(Supplier<R> supplier) {
        if (!this.hasExecutedSuccess()) {
            try {
                R r = supplier.get();
                this.optionalR = Optional.of(Optional.ofNullable(r));
            }
            catch (Exception ex) {
                this.exception = new RuntimeException(ex);
            }
        }
        return this;
    }

    /**
     * 已执行成功
     *
     * @return boolean
     */
    private boolean hasExecutedSuccess() {
        return optionalR.isPresent();
    }

    /**
     * 获取执行结果
     *
     * @return R
     */
    public R get() {
        if (this.hasExecutedSuccess()) {
            return getR().orElse(null);
        }
        else {
            if (exception == null) {
                throw new NullPointerException("没有执行任何操作。");
            }
            else {
                throw exception;
            }
        }
    }

    /**
     * 执行失败返回默认值
     *
     * @param defValue 默认值
     * @return R
     */
    public R orElse(R defValue) {
        if (this.hasExecutedSuccess()) {
            return getR().orElse(null);
        }
        else {
            if (exception == null) {
                throw new NullPointerException("没有执行任何操作。");
            }
            else {
                return defValue;
            }
        }
    }

    /**
     * 执行失败就执行指定函数
     *
     * @param other 指定函数
     * @return R
     */
    public R orElseGet(Supplier<? extends R> other) {
        if (this.hasExecutedSuccess()) {
            return getR().orElse(null);
        }
        else {
            return other.get();
        }
    }

    /**
     * 执行失败就抛出异常
     *
     * @param exceptionSupplier 抛出异常
     * @return R
     */
    public <X extends Throwable> R orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.hasExecutedSuccess()) {
            return getR().orElse(null);
        }
        else {
            throw exceptionSupplier.get();
        }
    }
}
