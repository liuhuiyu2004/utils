package com.liuhuiyu.core.lang.function_interface;

import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-23 16:49
 */
@FunctionalInterface
public interface MapToT<T> {
    /**
     * Object 转换成 类型 T
     * @param map Map<String,Object>
     * @return T
     */
    T mapToT(Map<String,Object> map);
}
