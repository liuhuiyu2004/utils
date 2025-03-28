package com.liuhuiyu.core.lang.function_interface;

import java.util.Map;

/**
 * 功能<p>
 * Created on 2025/3/28 21:10
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@FunctionalInterface
public interface MapToT<T> {
    /**
     * Object 转换成 类型 T
     * @param map Map&lt;String,Object>
     * @return T
     */
    T mapToT(Map<String,Object> map);
}
