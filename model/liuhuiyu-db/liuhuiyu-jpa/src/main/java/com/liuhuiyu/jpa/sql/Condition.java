package com.liuhuiyu.jpa.sql;

import java.util.Collection;

/**
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
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 15:14
 */
public interface Condition<T> {

    /**
     * like值
     *
     * @param value 值
     *              Created DateTime 2022-06-02 18:43
     */
    AbstractSqlCommandPackage<T> likeValue(String value);

    /**
     * like值
     *
     * @param value 值
     * @param trim  去掉首尾空格
     * @param head  头部模糊匹配
     * @param tail  尾部模糊匹配
     *              Created DateTime 2022-06-02 18:43
     */
    AbstractSqlCommandPackage<T> likeValue(String value, Boolean trim, Boolean head, Boolean tail);

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     *             Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T> inPackage(P[] data);

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     *             Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T> inPackage(Collection<P> data);

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     *               Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T> inPackage(Collection<P> data, Boolean notIn, Boolean isNull);

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     *               Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T> inPackage(P[] data, Boolean notIn, Boolean isNull);

    <P> AbstractSqlCommandPackage<T> between(P beginValue, P endValue);

    /**
     * 封装 数据段互相包含（开区间 位置相同）条件
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     *                 Created DateTime 2022-12-01 10:23
     */
    <P> AbstractSqlCommandPackage<T> inclusion(P minValue, P maxValue);

    /**
     * 等于
     *
     * @param value 值
     *              Created DateTime 2023-02-23 23:51
     */
    <P> AbstractSqlCommandPackage<T> eq(P value);

    /**
     * <> 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T> ne(P value);

    /**
     * > 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T> gt(P value);

    /**
     * < 比较
     *
     * @param value 值
     * @param <P>   p
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T> lt(P value);

    /**
     * >= 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T> ge(P value);

    /**
     * <= 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T> le(P value);

    /**
     * 为 null
     * <p>
     * Created DateTime 2023-03-25 9:23
     */
    AbstractSqlCommandPackage<T> isNull();

    /**
     * 为 null
     * <p>
     * Created DateTime 2023-03-25 9:23
     */
    AbstractSqlCommandPackage<T> isNotNull();

    /**
     * 表达式
     *
     * @param expression 表达式
     * @return cc.rehome.db.jpa.sql.AbstractSqlCommandPackage<T>
     * @author LiuHuiYu
     * Created DateTime 2024-01-14 21:44
     */
    AbstractSqlCommandPackage<T> expression(String expression);

}
