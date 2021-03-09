package com.liuhuiyu.util.model;

import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-07-08 11:44
 */
@Data
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

    public boolean isSuccess() {
        return this.flag.equals(OK);
    }

    /**
     * 通过静态方法获取实例
     */
    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull Result<T> of(T data) {
        return new Result<>(data);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T> @NotNull Result<T> of(T data, Integer flag) {
        return new Result<>(data, flag);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static <T> @NotNull Result<T> of(T data, Integer flag, String msg) {
        return new Result<>(data, flag, msg);
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull Result<T> error(String msg) {
        return new Result<>(null, ERROR, msg);
    }

    @Contract(value = " -> new", pure = true)
    public static <T> @NotNull Result<T> success() {
        return new Result<>(null, OK, "");
    }

    private static String FLAG_KEY = "flag";
    private static String MSG_KEY = "msg";
    private static String DATA_KEY = "data";

    public static <T> @NotNull Result<T> ofMap(Map<String, Object> map, Class<T> clazz) {
        Result<T> result = new Result<>();
        if (map.containsKey(FLAG_KEY)) {
            result.flag = ((Number) map.get(FLAG_KEY)).intValue();
        }
        else {
            throw new RuntimeException("缺少关键字" + FLAG_KEY);
        }
        if (map.containsKey(MSG_KEY)) {
            result.msg = map.get(MSG_KEY).toString();
        }
        else {
            throw new RuntimeException("缺少关键字" + MSG_KEY);
        }
        if (map.containsKey(DATA_KEY)) {
            if (clazz.isInstance(map.get(DATA_KEY))) {
                result.data = clazz.cast(map.get(DATA_KEY));
            }
            else {
                throw new RuntimeException("Map 关键字‘" + DATA_KEY + "’无法转换为当前类型。");
            }
        }
        else {
            throw new RuntimeException("缺少关键字" + DATA_KEY);
        }
        return result;
    }
}