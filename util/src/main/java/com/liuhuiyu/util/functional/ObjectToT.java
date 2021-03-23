package com.liuhuiyu.util.functional;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-23 16:48
 */
@FunctionalInterface
public interface ObjectToT<T> {
    /**
     * Object 转换成 类型 T
     * @param o Object
     * @return T
     */
    T objectToT(Object o);
}
