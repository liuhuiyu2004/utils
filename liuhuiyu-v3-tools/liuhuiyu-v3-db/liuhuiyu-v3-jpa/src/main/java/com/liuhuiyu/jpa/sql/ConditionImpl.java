package com.liuhuiyu.jpa.sql;

import com.liuhuiyu.core.help.sql.SelectSql;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
 * Created DateTime 2023-10-19 15:39
 */
public class ConditionImpl<T> implements Condition<T, ConditionImpl<T>> {
    protected final AbstractSqlCommandPackage<T, ConditionImpl<T>> sqlCommandPackage;
    protected String fieldName;
    protected String minFieldName;
    protected String maxFieldName;
    protected String condition;

    public ConditionImpl(AbstractSqlCommandPackage<T, ConditionImpl<T>> sqlCommandPackage) {
        this.sqlCommandPackage = sqlCommandPackage;
    }

    private void checkField() {
//        if (!StringUtils.hasText(fieldName)) {
//            throw new RuntimeException("未设定字段名称");
//        }
    }

    private void checkField2() {
//        if (!StringUtils.hasText(minFieldName) || !StringUtils.hasText(maxFieldName)) {
//            throw new RuntimeException("双字段名称设定不完整。");
//        }
    }

    /**
     * like值
     *
     * @param value 值
     *              Created DateTime 2022-06-02 18:43
     */
    @Override
    public AbstractSqlCommandPackage<T, ConditionImpl<T>> likeValue(String value) {
        return likeValue(value, true, true, true);
    }

    /**
     * like值
     *
     * @param value 值
     * @param trim  去掉首尾空格
     * @param head  头部模糊匹配
     * @param tail  尾部模糊匹配
     *              Created DateTime 2022-06-02 18:43
     */
    @Override
    public AbstractSqlCommandPackage<T, ConditionImpl<T>> likeValue(String value, Boolean trim, Boolean head, Boolean tail) {
        this.checkField();
        if (value == null) {
            return this.sqlCommandPackage;
        }
        final String s = (head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : "");
        this.sqlCommandPackage.getSelectSql().getSqlWhere().append(condition).append("(").append(this.fieldName).append(" LIKE ").append("?").append(")");
        this.sqlCommandPackage.getSelectSql().getParameterList().add(s);
        return this.sqlCommandPackage;
    }

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     *             Created DateTime 2022-11-20 8:28
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> inPackage(P[] data) {
        return inPackage(data, false, false);
    }

    /**
     * 封装 in 条件
     *
     * @param collection 查询的数据
     *                   Created DateTime 2022-11-20 8:28
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> inPackage(Collection<P> collection) {
        return inPackage(collection, false, false);
    }

    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> inPackage(Collection<P> collection, Boolean notIn, Boolean isNull) {
        this.checkField();
        if (collection != null && !collection.isEmpty()) {
            this.sqlCommandPackage.getSelectSql().getSqlWhere().append(condition).append("((").append(fieldName).append(notIn ? " NOT" : "").append(" IN(");
            String separator = "";
            for (P datum : collection) {
                this.sqlCommandPackage.getSelectSql().getSqlWhere().append(separator).append("?");
                separator = ",";
                this.sqlCommandPackage.getSelectSql().getParameterList().add(datum);
            }
            this.sqlCommandPackage.getSelectSql().getSqlWhere().append("))");
            if (isNull) {
                this.sqlCommandPackage.getSelectSql().getSqlWhere().append("OR(").append(fieldName).append(" is null)");
            }
            this.sqlCommandPackage.getSelectSql().getSqlWhere().append(")");
        }
        return this.sqlCommandPackage;
    }

    /**
     * 封装 in 条件
     *
     * @param data   查询的数据
     * @param notIn  使用 not in
     * @param isNull 包含空 OR(fieldName is null)
     *               Created DateTime 2022-11-20 8:28
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> inPackage(P[] data, Boolean notIn, Boolean isNull) {
        this.checkField();
        final List<P> list = Arrays.asList(data);
        return inPackage(list, notIn, isNull);
    }

    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> between(P beginValue, P endValue) {
        this.checkField();
        if (beginValue == null || endValue == null) {
            return this.sqlCommandPackage;
        }
        this.sqlCommandPackage.getSelectSql().getSqlWhere().append(condition)
                .append("(").append(this.fieldName)
                .append(" between ? and ?)");
        this.sqlCommandPackage.getSelectSql().getParameterList().add(beginValue);
        this.sqlCommandPackage.getSelectSql().getParameterList().add(endValue);
        return this.sqlCommandPackage;
    }

    /**
     * 封装 数据段互相包含（开区间 位置相同）条件
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     *                 Created DateTime 2022-12-01 10:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> inclusion(P minValue, P maxValue) {
        this.checkField2();
        if (minValue == null || maxValue == null) {
            return this.sqlCommandPackage;
        }
        this.sqlCommandPackage.getSelectSql().getSqlWhere().append(condition).append(" (");
        this.sqlCommandPackage.getSelectSql().getSqlWhere().append("((").append(minFieldName).append(" < ?").append(") and (").append(maxFieldName).append(" > ?").append("))");
        this.sqlCommandPackage.getSelectSql().getParameterList().add(minValue);
        this.sqlCommandPackage.getSelectSql().getParameterList().add(minValue);

        this.sqlCommandPackage.getSelectSql().getSqlWhere().append("or");
        this.sqlCommandPackage.getSelectSql().getSqlWhere().append("((").append(minFieldName).append(" < ?").append(") and (").append(maxFieldName).append(" > ?").append("))");
        this.sqlCommandPackage.getSelectSql().getParameterList().add(maxValue);
        this.sqlCommandPackage.getSelectSql().getParameterList().add(maxValue);

        this.sqlCommandPackage.getSelectSql().getSqlWhere().append("or");
        this.sqlCommandPackage.getSelectSql().getSqlWhere().append("((").append(minFieldName).append(" >= ?").append(") and (").append(maxFieldName).append(" <= ?").append("))");
        this.sqlCommandPackage.getSelectSql().getSqlWhere().append(")");
        this.sqlCommandPackage.getSelectSql().getParameterList().add(minValue);
        this.sqlCommandPackage.getSelectSql().getParameterList().add(maxValue);
        return this.sqlCommandPackage;
    }

    /**
     * 等于
     *
     * @param value 值
     *              Created DateTime 2023-02-23 23:51
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> eq(P value) {
        return this.generate("=", value);
    }

    /**
     * &lt; &gt; 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> ne(P value) {
        return this.generate("<>", value);
    }

    /**
     * > 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> gt(P value) {
        return this.generate(">", value);
    }

    /**
     * &lt; 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> lt(P value) {
        return this.generate("<", value);
    }

    /**
     * 大于等于 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> ge(P value) {
        return this.generate(">=", value);
    }

    /**
     * 小于等于 比较
     *
     * @param value 值
     *              Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> le(P value) {
        return this.generate("<=", value);
    }

    /**
     * 为 null
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public AbstractSqlCommandPackage<T, ConditionImpl<T>> isNull() {
        this.checkField();
        this.sqlCommandPackage.getSelectSql().getSqlWhere()
                .append(condition)
                .append("(").append(this.fieldName)
                .append(" is null )");
        return this.sqlCommandPackage;
    }

    /**
     * 为 null
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public AbstractSqlCommandPackage<T, ConditionImpl<T>> isNotNull() {
        this.checkField();
        this.sqlCommandPackage.getSelectSql().getSqlWhere()
                .append(condition)
                .append("(").append(this.fieldName)
                .append(" is not null )");
        return this.sqlCommandPackage;
    }

    @Override
    public AbstractSqlCommandPackage<T, ConditionImpl<T>> expression(String expression) {
        this.sqlCommandPackage.getSelectSql().getSqlWhere()
                .append(this.condition)
                .append("(")
                .append(expression)
                .append(")");
        return this.sqlCommandPackage;
    }

    private <P> AbstractSqlCommandPackage<T, ConditionImpl<T>> generate(String operator, P value) {
        this.checkField();
        if (value != null) {
            this.sqlCommandPackage.getSelectSql().getSqlWhere()
                    .append(condition)
                    .append("(").append(this.fieldName)
                    .append(" ").append(operator)
                    .append(" ?").append(")");
            this.sqlCommandPackage.getSelectSql().getParameterList().add(value);
        }
        return this.sqlCommandPackage;
    }

    @Override
    public AbstractSqlCommandPackage<T, ConditionImpl<T>> child(String operator, SelectSql value) {
        this.sqlCommandPackage.getSelectSql().getSqlWhere()
                .append(condition)
                .append("(").append(this.fieldName).append(operator)
                .append(value.getSql())
                .append(")");
        this.sqlCommandPackage.getSelectSql().getParameterList().addAll(value.getParameterList());
        return this.sqlCommandPackage;
    }
}
