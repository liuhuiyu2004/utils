package com.liuhuiyu.jpa;

/**
 * 函数式(obj->T)
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-22 9:10
 */
@FunctionalInterface
public interface DaoOperator<T> {
    /**
     * Object 转换成 类型 T
     * @param o Object
     * @return T
     */
    T objectToT(Object o);
}