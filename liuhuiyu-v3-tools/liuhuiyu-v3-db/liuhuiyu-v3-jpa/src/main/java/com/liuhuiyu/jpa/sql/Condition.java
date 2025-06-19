package com.liuhuiyu.jpa.sql;

import org.springframework.util.StringUtils;

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
public class Condition {
    protected final ConditionalFiltering conditionalFiltering;
    private final String field1;
    private final String field2;
    private final String condition;

    public Condition(ConditionalFiltering conditionalFiltering, String field1, String condition) {
        this(conditionalFiltering, field1, null, condition);
    }

    public Condition(ConditionalFiltering conditionalFiltering, String field1, String field2, String condition) {
        this.conditionalFiltering = conditionalFiltering;
        this.field1 = field1;
        this.field2 = field2;
        this.condition = condition;
    }

    private void checkField() {
        if (!StringUtils.hasText(field1)) {
            throw new RuntimeException("未设定字段名称");
        }
    }

    private void checkTwoField() {
        if (!StringUtils.hasText(field1) || !StringUtils.hasText(field2)) {
            throw new RuntimeException("双字段名称设定不完整。");
        }
    }

    /**
     * like值
     *
     * @param value 值
     */
    public ConditionalFiltering likeValue(String value) {
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
    public ConditionalFiltering likeValue(String value, Boolean trim, Boolean head, Boolean tail) {
        this.checkField();
        if (value == null) {
            return this.conditionalFiltering;
        }
        final String s = (head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : "");
        this.conditionalFiltering.getConditional().append(this.condition).append("(").append(this.field1).append(" LIKE ").append("?").append(")");
        this.conditionalFiltering.getParameterList().add(s);
        return this.conditionalFiltering;
    }

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     */
    public <E> ConditionalFiltering inPackage(E[] data) {
        return inPackage(data, false, false);
    }

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     */
    public <E> ConditionalFiltering inPackage(Collection<E> data) {
        return inPackage(data, false, false);
    }

    /**
     * 封装 in 条件
     *
     * @param collection 查询的数据
     * @param notIn      使用 not in
     * @param isNull     包含空 OR(fieldName is null)
     */
    public <E> ConditionalFiltering inPackage(Collection<E> collection, Boolean notIn, Boolean isNull) {
        this.checkField();
        if (collection == null || collection.isEmpty()) {
            return this.conditionalFiltering;
        }
        this.conditionalFiltering.getConditional().append(condition).append("((").append(field1).append(notIn ? " NOT" : "").append(" IN(");
        String separator = "";
        for (E datum : collection) {
            this.conditionalFiltering.getConditional().append(separator).append("?");
            separator = ",";
            this.conditionalFiltering.getParameterList().add(datum);
        }
        this.conditionalFiltering.getConditional().append("))");
        if (isNull) {
            this.conditionalFiltering.getConditional().append("OR(").append(field1).append(" is null)");
        }
        this.conditionalFiltering.getConditional().append(")");
        return this.conditionalFiltering;
    }

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     */
    public <E> ConditionalFiltering inPackage(E[] data, Boolean notIn, Boolean isNull) {
        return this.inPackage(Arrays.asList(data), notIn, isNull);
    }

    /**
     * between闭区间
     *
     * @param beginValue 开始值
     * @param endValue   结束值
     */
    public ConditionalFiltering between(Object beginValue, Object endValue) {
        this.checkField();
        if (beginValue == null || endValue == null) {
            return this.conditionalFiltering;
        }
        this.conditionalFiltering.getConditional().append(condition)
                .append("((").append(this.field1)
                .append(">= ?)AND(")
                .append(this.field1)
                .append("<= ?))");
        this.conditionalFiltering.getParameterList().add(beginValue);
        this.conditionalFiltering.getParameterList().add(endValue);
        return this.conditionalFiltering;
    }

    /**
     * 封装 数据段互相包含（开区间 位置相同）条件
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     */
    public ConditionalFiltering inclusion(Object minValue, Object maxValue) {
        return this.inclusion(minValue, maxValue, true);
    }

    /**
     * 封装 数据段互相包含（开区间 位置相同）条件
     *
     * @param minValue     最小值
     * @param maxValue     最大值
     * @param openInterval 开区间
     */
    public ConditionalFiltering inclusion(Object minValue, Object maxValue, boolean openInterval) {
        this.checkTwoField();
        if (minValue == null || maxValue == null) {
            return this.conditionalFiltering;
        }
        this.conditionalFiltering.getConditional()
                .append(condition)
                .append("((").append(this.field2)
                .append(openInterval ? " > " : " >= ")
                .append(")")
                .append(" and (").append(this.field1)
                .append(openInterval ? " < " : " <= ")
                .append("?))");
        this.conditionalFiltering.getParameterList().add(minValue);
        this.conditionalFiltering.getParameterList().add(maxValue);
        return this.conditionalFiltering;
    }

    /**
     * 等于
     *
     * @param value 值
     */
    public <P> ConditionalFiltering eq(P value) {
        return this.appendCondition(" = ", value);
    }

    /**
     * != 比较
     *
     * @param value 值
     */
    public <P> ConditionalFiltering ne(P value) {
        return this.appendCondition(" != ", value);
    }

    /**
     * > 比较
     *
     * @param value 值
     */
    public <P> ConditionalFiltering gt(P value) {
        return this.appendCondition(" > ", value);
    }

    /**
     * &lt; 比较
     *
     * @param value 值
     * @param <P>   p
     */
    public <P> ConditionalFiltering lt(P value) {
        return this.appendCondition(" < ", value);
    }

    /**
     * >= 比较
     *
     * @param value 值
     */
    public <P> ConditionalFiltering ge(P value) {
        return this.appendCondition(" >= ", value);
    }

    /**
     * &lt;= 比较
     *
     * @param value 值
     */
    public <P> ConditionalFiltering le(P value) {
        return this.appendCondition(" <= ", value);
    }

    /**
     * 为 null
     * <p>
     */
    public ConditionalFiltering isNull() {
        this.checkField();
        this.conditionalFiltering.getConditional().append(condition).append("(").append(field1).append(" is null )");
        return this.conditionalFiltering;
    }

    /**
     * 为 null
     * <p>
     */
    public ConditionalFiltering isNotNull() {
        this.checkField();
        this.conditionalFiltering.getConditional().append(condition).append("(").append(field1).append(" is not null )");
        return this.conditionalFiltering;
    }

    /**
     * 表达式<p>
     *
     * @param expression 表达式 （函数等）
     * @return com.liuhuiyu.jpa.sql.ConditionalFiltering
     */
    public ConditionalFiltering expression(String expression) {
        this.conditionalFiltering.getConditional().append(condition).append("(").append(expression).append(")");
        return this.conditionalFiltering;
    }

    /**
     * 子语句匹配
     *
     * @param operator       操作符（常用in）
     * @param childSelectSql 子语句
     */
    public ConditionalFiltering child(String operator, ConditionalFiltering childSelectSql) {
        this.checkField();
        this.conditionalFiltering.getConditional()
                .append(condition)
                .append("(")
                .append(field1)
                .append(" ")
                .append(operator)
                .append(" (").append(childSelectSql.getConditional())
                .append("))");
        this.conditionalFiltering.getParameterList().addAll(childSelectSql.getParameterList());
        return this.conditionalFiltering;
    }

    // 私有方法：添加条件
    private ConditionalFiltering appendCondition(String operator, Object value) {
        this.checkField();
        this.conditionalFiltering.getConditional().append(this.condition)
                .append("(").append(field1).append(" ").append(operator).append(" ?)");
        this.conditionalFiltering.getParameterList().add(value);
        return this.conditionalFiltering;
    }
    //endregion
}
