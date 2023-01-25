package com.liuhuiyu.util.list;

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
    private Supplier<R> defineSupplier;
    private Optional<R> resOptional;

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
        if (isAllowExecution(b)) {
            this.resOptional = Optional.ofNullable(f.apply(t));
        }
        return this;
    }

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
     * @author LiuHuiYu
     * Created DateTime 2023-01-24 14:10
     * @return boolean
     */
    @SuppressWarnings("all")
    private boolean isAlreadyExecution() {
        return null != this.resOptional;
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
//        if (this.supplier == null && this.function == null && b) {
//            this.supplier = supplier;
//        }
        if (this.isAllowExecution(b)) {
            this.resOptional = Optional.ofNullable(supplier.get());
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
        if (this.isAlreadyExecution()) {
            return this.resOptional;
        }
        else if (this.defineFunction != null) {
            this.resOptional = Optional.ofNullable(this.defineFunction.apply(t));
            return this.resOptional;
        }
        else if (this.defineSupplier != null) {
            this.resOptional = Optional.ofNullable(this.defineSupplier.get());
        }
        else if (this.defineValue != null) {
            this.resOptional = Optional.of(defineValue);
        }
        else if (this.exception != null) {
            throw exception;
        }
        else {
            return Optional.empty();
        }
        return this.resOptional;
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
        this.run();
        if (this.isAlreadyExecution()) {
            return this.resOptional.orElse(null);
        }
        else {
            return other.get();
        }
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
        this.run();
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
        this.run();
        if (this.isAlreadyExecution()) {
            return this.resOptional.orElse(null);
        }
        else {
            return r;
        }
    }

    public static IfRun<Void, Void> ifRun() {
        return new IfRun<>();
    }

    public IfRun<T, R> ifRun(Boolean b, Runnable execution) {
        if (this.isAllowExecution(b)) {
            execution.run();
            this.resOptional = Optional.empty();
        }
        return this;
    }
}