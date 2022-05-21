package com.liuhuiyu.util.list;

import java.util.Optional;
import java.util.function.Function;

/**
 * 链式多条件结果返回
 * 多个条件符合的情况下只执行第一个符合条件的表达式并返回结果
 * @author LiuHuiYu
 * Created DateTime 2022-05-21 15:41
 */
public class IfRun<T, R> {
    T t;
    R defineValue;
    RuntimeException exception;
    Function<T, R> f;

    public IfRun(T t) {
        this.t = t;
    }

    public IfRun(T t, R defineValue) {
        this.t = t;
        this.defineValue = defineValue;
    }

    public IfRun(T t, RuntimeException exception) {
        this.t = t;
        this.exception = exception;
    }

    public IfRun<T, R> add(Boolean b, Function<T, R> f) {
        if (this.f == null && b) {
            this.f = f;
        }
        return this;
    }

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