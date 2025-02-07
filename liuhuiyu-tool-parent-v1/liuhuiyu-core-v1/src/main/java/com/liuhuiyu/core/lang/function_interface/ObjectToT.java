package com.liuhuiyu.core.lang.function_interface;

/**
 * 将对象转换为指定类型
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 11:40
 */
public interface ObjectToT<T> {
    /**
     * Object 转换成 类型 T
     *
     * @param o Object
     * @return T
     */
    T objectToT(Object o);
}
