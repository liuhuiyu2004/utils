package com.liuhuiyu.util.list;

import java.util.Optional;
import java.util.function.Function;

/**
 * 链式多条件执行并返回结果
 * 多个条件符合的情况下只执行第一个符合条件的表达式并返回结果
 * @author LiuHuiYu
 * Created DateTime 2022-05-21 15:41
 */
public class IfRun<T, R> {
    private final T t;
    private R defineValue;
    private RuntimeException exception;
    private Function<T, R> f;

    /**
     * 链式多条件执行并返回结果，如果都没有执行将返回默认
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:16
     * @param t 输出参数
     */
    public IfRun(T t) {
        this.t = t;
    }
    /**
     * 链式多条件执行并返回结果，如果都没有执行将返回默认
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:15
     * @param t           输入参数
     * @param defineValue 默认值
     */
    public IfRun(T t, R defineValue) {
        this.t = t;
        this.defineValue = defineValue;
    }
    /**
     * 链式多条件执行并返回结果，如果都没有执行将抛出异常
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     * @param t         输入参数
     * @param exception 抛出异常
     */
    public IfRun(T t, RuntimeException exception) {
        this.t = t;
        this.exception = exception;
    }
    /**
     * 添加执行
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:17
     * @param b 判断是否执行
     * @param f 要执行的函数
     * @return com.liuhuiyu.util.list.IfRun<T,R>
     */
    public IfRun<T, R> add(Boolean b, Function<T, R> f) {
        if (this.f == null && b) {
            this.f = f;
        }
        return this;
    }
    /**
     * 获取执行结果
     * @author LiuHuiYu
     * Created DateTime 2022-05-21 16:18
     * @return java.util.Optional<R>
     */
    public Optional<R> run() {
        if (this.f != null) {
            return Optional.of(f.apply(t));
        }
        else if (defineValue != null) {
            return Optional.of(defineValue);
        }
        else if (exception != null) {
            throw exception;
        }
        return Optional.empty();
    }
}