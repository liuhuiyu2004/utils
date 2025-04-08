package com.liuhuiyu.jpa.sql;

import java.util.Arrays;
import java.util.Collection;

/**
 * 条件封装(对where、having 条件进行封装)<p>
 * eq 就是 equal等于
 * ne就是 not equal不等于
 * gt 就是 greater than大于
 * lt 就是 less than小于
 * ge 就是 greater than or equal 大于等于
 * le 就是 less than or equal 小于等于
 * in 就是 in 包含（数组）
 * isNull 就是 等于null
 * between 就是 在2个条件之间(包括边界值)
 * like就是 模糊查询
 * Created on 2025/4/8 21:41
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface Condition<T, U extends Condition<T, U>> {

    /**
     * like值
     *
     * @param value 值
     */
    default ConditionalFiltering likeValue(String value) {
        return likeValue(value, true, true, true);
    }

    /**
     * like值
     *
     * @param value 值
     * @param trim  去掉首尾空格
     * @param head  头部模糊匹配
     * @param tail  尾部模糊匹配
     */
    ConditionalFiltering likeValue(String value, Boolean trim, Boolean head, Boolean tail);

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     */
    default <E> ConditionalFiltering inPackage(E[] data) {
        return inPackage(data, false, false);
    }

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     */
    default <E> ConditionalFiltering inPackage(Collection<E> data) {
        return inPackage(data, false, false);
    }

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     */
    <E> ConditionalFiltering inPackage(Collection<E> data, Boolean notIn, Boolean isNull);

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     */
    <E> ConditionalFiltering inPackage(E[] data, Boolean notIn, Boolean isNull);

    /**
     * between闭区间
     *
     * @param beginValue 开始值
     * @param endValue   结束值
     */
    <E> ConditionalFiltering between(E beginValue, E endValue);

    /**
     * 封装 数据段互相包含（开区间 位置相同）条件
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     */
    <E> ConditionalFiltering inclusion(E minValue, E maxValue);

    /**
     * 等于
     *
     * @param value 值
     */
    <P> ConditionalFiltering eq(P value);

    /**
     * &lt;> 比较
     *
     * @param value 值
     */
    <P> ConditionalFiltering ne(P value);

    /**
     * > 比较
     *
     * @param value 值
     */
    <P> ConditionalFiltering gt(P value);

    /**
     * &lt; 比较
     *
     * @param value 值
     * @param <P>   p
     */
    <P> ConditionalFiltering lt(P value);

    /**
     * >= 比较
     *
     * @param value 值
     */
    <P> ConditionalFiltering ge(P value);

    /**
     * &lt;= 比较
     *
     * @param value 值
     */
    <P> ConditionalFiltering le(P value);

    /**
     * 为 null
     * <p>
     */
    ConditionalFiltering isNull();

    /**
     * 为 null
     * <p>
     */
    ConditionalFiltering isNotNull();

    /**
     * 表达式<p>
     * @param expression 表达式 （函数等）
     * @return com.liuhuiyu.jpa.sql.ConditionalFiltering
     */
    ConditionalFiltering expression(String expression);

    /**
     * 子语句匹配
     *
     * @param operator 操作符（常用in）
     * @param childSelectSql 子语句
     */
    ConditionalFiltering child(String operator, SelectSql childSelectSql);
}
