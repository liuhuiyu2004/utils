package com.liuhuiyu.web.help;

import java.io.Serializable;

/**
 * 功能<p>
 * Created on 2025/3/24 21:03
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class Result<T> implements Serializable {
    public static final int OK = 0;
    public static final int ERROR = -1;

    /**
     * 返回码
     */
    private Integer flag;
    /**
     * 信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public Result() {
        this.flag = OK;
        this.msg = "操作成功";
        this.data = null;
    }

    private Result(T data) {
        this();
        this.data = data;
    }

    private Result(T data, Integer flag) {
        this();
        this.data = data;
        this.flag = flag;
    }

    private Result(T data, Integer flag, String msg) {
        this();
        this.data = data;
        this.flag = flag;
        this.msg = msg;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.flag.equals(OK);
    }

    /**
     * 通过静态方法获取实例
     */
    public static <T> Result<T> of(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> of(T data, Integer flag) {
        return new Result<>(data, flag);
    }

    public static <T> Result<T> of(T data, Integer flag, String msg) {
        return new Result<>(data, flag, msg);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(null, ERROR, msg);
    }

    public static <T> Result<T> error(String msg, int errorCode) {
        return new Result<>(null, errorCode, msg);
    }

    public static <T> Result<T> success() {
        return new Result<>(null, OK, "");
    }
}
