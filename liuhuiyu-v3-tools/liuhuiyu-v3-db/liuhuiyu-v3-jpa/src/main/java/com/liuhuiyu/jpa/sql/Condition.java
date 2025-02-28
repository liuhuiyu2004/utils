package com.liuhuiyu.jpa.sql;

import com.liuhuiyu.core.help.sql.SelectSql;

import java.util.Collection;
import java.util.List;

/**
 * 条件封装(对where进行封装)
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
public interface Condition<T, U extends Condition<T, U>> {

    /**
     * like值
     *
     * @param value 值
     *              Created DateTime 2022-06-02 18:43
     */
    AbstractSqlCommandPackage<T, U> likeValue(String value);

    /**
     * like值
     *
     * @param value 值
     * @param trim  去掉首尾空格
     * @param head  头部模糊匹配
     * @param tail  尾部模糊匹配
     *              Created DateTime 2022-06-02 18:43
     */
    AbstractSqlCommandPackage<T, U> likeValue(String value, Boolean trim, Boolean head, Boolean tail);

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     *             Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T, U> inPackage(P[] data);

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     *             Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T, U> inPackage(Collection<P> data);

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     *               Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T, U> inPackage(Collection<P> data, Boolean notIn, Boolean isNull);

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     *               Created DateTime 2022-11-20 8:28
     */
    <P> AbstractSqlCommandPackage<T, U> inPackage(P[] data, Boolean notIn, Boolean isNull);

    <P> AbstractSqlCommandPackage<T, U> between(P beginValue, P endValue);

    /**
     * 封装 数据段互相包含（开区间 位置相同）条件
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     *                 Created DateTime 2022-12-01 10:23
     */
    <P> AbstractSqlCommandPackage<T, U> inclusion(P minValue, P maxValue);

    /**
     * 等于
     *
     * @param value 值
     *              Created DateTime 2023-02-23 23:51
     */
    <P> AbstractSqlCommandPackage<T, U> eq(P value);

    /**
     * &lt;> 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T, U> ne(P value);

    /**
     * > 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T, U> gt(P value);

    /**
     * &lt; 比较
     *
     * @param value 值
     * @param <P>   p
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T, U> lt(P value);

    /**
     * >= 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T, U> ge(P value);

    /**
     * &lt;= 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    <P> AbstractSqlCommandPackage<T, U> le(P value);

    /**
     * 为 null
     * <p>
     * Created DateTime 2023-03-25 9:23
     */
    AbstractSqlCommandPackage<T, U> isNull();

    /**
     * 为 null
     * <p>
     * Created DateTime 2023-03-25 9:23
     */
    AbstractSqlCommandPackage<T, U> isNotNull();

    /**
     * 表达式
     *
     * @param expression 表达式
     * @return com.liuhuiyu.db.jpa.sql.AbstractSqlCommandPackage&lt;T, Condition &lt;T>>
     * Created DateTime 2024-01-14 21:44
     */
    AbstractSqlCommandPackage<T, U> expression(String expression);

    /**
     * 子语句匹配
     *
     * @param value 值
     *              Created DateTime 2022-06-02 18:43
     */
    AbstractSqlCommandPackage<T, U> child(String operator, SelectSql value);

}
