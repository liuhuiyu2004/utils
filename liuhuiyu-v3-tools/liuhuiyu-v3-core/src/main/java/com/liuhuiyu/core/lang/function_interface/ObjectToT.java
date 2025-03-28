package com.liuhuiyu.core.lang.function_interface;

/**
 * 功能<p>
 * Created on 2025/3/28 21:10
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
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
