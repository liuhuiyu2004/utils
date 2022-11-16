package com.liuhuiyu.util.list;

import com.liuhuiyu.util.functional.Execution;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 链式多条件执行并返回结果
 * 多个条件符合的情况下只执行第一个符合条件的表达式并返回结果
 *
 * @author LiuHuiYu
 * Created DateTime 2022-05-21 15:41
 */
public class IfRun<T, R> {
    private final T t;
    private R defineValue;
    private RuntimeException exception;
    private Function<T, R> defineFunction;
    private Function<T, R> function;
    private Supplier<R> supplier;
    private Supplier<R> defineSupplier;

    private Execution execution;

    public static <T, R> IfRun<T, R> ifRun(T t) {
        return new IfRun<>(t);
    }

    public IfRun() {
        this.t = null;
    }

    /**
     * 链式多条件执行并返回结果，如果都没有执行将返回默认
     *
     * @param t 输出参数
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:16
     */
    public IfRun(T t) {
        this.t = t;
    }

    /**
     * 链式多条件执行并返回结果，如果都没有执行将返回默认
     *
     * @param t           输入参数
     * @param defineValue 默认值
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:15
     */
    public IfRun(T t, R defineValue) {
        this.t = t;
        this.defineValue = defineValue;
    }

    /**
     * 链式多条件执行并返回结果，如果都没有执行将抛出异常
     *
     * @param t         输入参数
     * @param exception 抛出异常
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     */
    public IfRun(T t, RuntimeException exception) {
        this.t = t;
        this.exception = exception;
    }

    /**
     * 链式多条件执行并返回结果，如果都没有执行将执行默认函数
     *
     * @param t              输入参数
     * @param defineFunction 默认执行
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     */
    public IfRun(T t, Function<T, R> defineFunction) {
        this.t = t;
        this.defineFunction = defineFunction;
    }

    /**
     * 链式多条件执行并返回结果，如果都没有执行将执行默认函数
     *
     * @param t              输入参数
     * @param defineSupplier 默认执行
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     */
    public IfRun(T t, Supplier<R> defineSupplier) {
        this.t = t;
        this.defineSupplier = defineSupplier;
    }

    public void setDefineValue(R defineValue) {
        this.defineValue = defineValue;
    }

    public void setRuntimeException(RuntimeException exception) {
        this.exception = exception;
    }

    public void setDefineFunction(Function<T, R> defineFunction) {
        this.defineFunction = defineFunction;
    }

    public void setDefineSupplier(Supplier<R> defineSupplier) {
        this.defineSupplier = defineSupplier;
    }

    /**
     * 添加执行
     *
     * @param b 判断是否执行
     * @param f 要执行的函数
     * @return com.liuhuiyu.util.list.IfRun<T, R>
     * @author LiuHuiYu
     * @deprecated 使用ifRun函数
     * Created DateTime 2022-05-21 16:17
     */
    @Deprecated
    public IfRun<T, R> add(Boolean b, Function<T, R> f) {
        return this.ifRun(b, f);
    }

    /**
     * 添加执行
     *
     * @param b 判断是否执行
     * @param f 要执行的函数
     * @return com.liuhuiyu.util.list.IfRun<T, R>
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     */
    public IfRun<T, R> ifRun(Boolean b, Function<T, R> f) {
        if (this.supplier == null && this.function == null && b) {
            this.function = f;
        }
        return this;
    }

    /**
     * 添加执行
     *
     * @param b        判断是否执行
     * @param supplier 要执行的函数
     * @return com.liuhuiyu.util.list.IfRun<T, R>
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     * @deprecated 使用ifRun函数
     */
    @Deprecated
    public IfRun<T, R> add(Boolean b, Supplier<R> supplier) {
        return this.ifRun(b, supplier);
    }

    /**
     * 添加执行
     *
     * @param b        判断是否执行
     * @param supplier 要执行的函数
     * @return com.liuhuiyu.util.list.IfRun<T, R>
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     */
    public IfRun<T, R> ifRun(Boolean b, Supplier<R> supplier) {
        if (this.supplier == null && this.function == null && b) {
            this.supplier = supplier;
        }
        return this;
    }

    /**
     * 获取执行结果
     *
     * @return java.util.Optional<R>
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:18
     */
    public Optional<R> run() {
        R r;
        if (this.function != null) {
            r = function.apply(t);
        }
        else if (this.supplier != null) {
            r = supplier.get();
        }
        else if (this.execution != null) {
            this.execution.run();
            return Optional.empty();
        }
        else if (this.defineFunction != null) {
            r = this.defineFunction.apply(t);
        }
        else if (this.defineSupplier != null) {
            r = this.defineSupplier.get();
        }
        else if (this.defineValue != null) {
            r = defineValue;
        }
        else if (this.exception != null) {
            throw exception;
        }
        else {
            return Optional.empty();
        }
        return r == null ? Optional.empty() : Optional.of(r);
    }

    /**
     * 没有可用将执行
     *
     * @param other 要执行的函数
     * @return R
     * @author LiuHuiYu
     * Created DateTime 2022-06-06 16:45
     */
    public R elseRun(Supplier<? extends R> other) {
        return run().orElseGet(other);
    }

    /**
     * 没有返回结构将抛出异常
     *
     * @param exceptionSupplier 将返回要抛出的异常的供应商
     * @return R
     * @author LiuHuiYu
     * Created DateTime 2022-06-06 16:51
     */
    public <X extends Throwable> R orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return run().orElseThrow(exceptionSupplier);
    }

    public R orElse(R r) {
        return run().orElse(r);
    }

    public static IfRun<Void, Void> ifRun() {
        return new IfRun<>();
    }

    public IfRun<T, R> ifRun(Boolean b, Execution execution) {
        if (this.supplier == null && this.function == null && b) {
            this.execution = execution;
        }
        return this;
    }
}