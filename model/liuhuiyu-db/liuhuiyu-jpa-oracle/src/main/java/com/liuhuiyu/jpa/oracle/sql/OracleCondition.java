package com.liuhuiyu.jpa.oracle.sql;

import com.liuhuiyu.jpa.sql.AbstractSqlCommandPackage;
import com.liuhuiyu.jpa.sql.Condition;
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
public class OracleCondition<T> implements Condition<T> {

    public OracleCondition(AbstractSqlCommandPackage<T> sqlCommandPackage) {
        this.sqlCommandPackage = sqlCommandPackage;
    }

    final AbstractSqlCommandPackage<T> sqlCommandPackage;
    String fieldName;
    String minFieldName;
    String maxFieldName;
    String condition;

    public AbstractSqlCommandPackage<T> getSqlCommandPackage() {
        return sqlCommandPackage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMinFieldName() {
        return minFieldName;
    }

    public void setMinFieldName(String minFieldName) {
        this.minFieldName = minFieldName;
    }

    public String getMaxFieldName() {
        return maxFieldName;
    }

    public void setMaxFieldName(String maxFieldName) {
        this.maxFieldName = maxFieldName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    private void checkField() {
        if (!StringUtils.hasText(fieldName)) {
            throw new RuntimeException("未设定字段名称");
        }
    }

    private void checkField2() {
        if (!StringUtils.hasText(minFieldName) || !StringUtils.hasText(maxFieldName)) {
            throw new RuntimeException("双字段名称设定不完整。");
        }
    }

    /**
     * like值
     *
     * @param value 值
     * @author LiuHuiYu
     * Created DateTime 2022-06-02 18:43
     */
    @Override
    public AbstractSqlCommandPackage<T> likeValue(String value) {
        return likeValue(value, true, true, true);
    }

    /**
     * like值
     *
     * @param value 值
     * @param trim  去掉首尾空格
     * @param head  头部模糊匹配
     * @param tail  尾部模糊匹配
     * @author LiuHuiYu
     * Created DateTime 2022-06-02 18:43
     */
    @Override
    public AbstractSqlCommandPackage<T> likeValue(String value, Boolean trim, Boolean head, Boolean tail) {
        this.checkField();
        if (trim ? StringUtils.hasText(value) : (value != null && !value.isEmpty())) {
            final String s = (head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : "");
            this.sqlCommandPackage.getSqlBuilder().append(condition).append("(").append(this.fieldName).append(" LIKE ").append("?").append(")");
            this.sqlCommandPackage.getParameterList().add(s);
        }
        return this.sqlCommandPackage;
    }

    /**
     * 封装 in 条件
     *
     * @param data 查询的数据
     * @author LiuHuiYu
     * Created DateTime 2022-11-20 8:28
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> inPackage(P[] data) {
        return inPackage(data, false, false);
    }

    /**
     * 封装 in 条件
     *
     * @param collection 查询的数据
     *                   Created DateTime 2022-11-20 8:28
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> inPackage(Collection<P> collection) {
        return inPackage(collection, false, false);
    }

    @Override
    public <P> AbstractSqlCommandPackage<T> inPackage(Collection<P> collection, Boolean notIn, Boolean isNull) {
        this.checkField();
        if (collection != null && !collection.isEmpty()) {
            this.sqlCommandPackage.getSqlBuilder().append(condition).append("((").append(fieldName).append(notIn ? " NOT" : "").append(" IN(");
            String separator = "";
            for (P datum : collection) {
                this.sqlCommandPackage.getSqlBuilder().append(separator).append("?");
                separator = ",";
                this.sqlCommandPackage.getParameterList().add(datum);
            }
            this.sqlCommandPackage.getSqlBuilder().append("))");
            if (isNull) {
                this.sqlCommandPackage.getSqlBuilder().append("OR(").append(fieldName).append(" is null)");
            }
            this.sqlCommandPackage.getSqlBuilder().append(")");
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
    public <P> AbstractSqlCommandPackage<T> inPackage(P[] data, Boolean notIn, Boolean isNull) {
        this.checkField();
        final List<P> list = Arrays.asList(data);
        return inPackage(list, notIn, isNull);
    }

    @Override
    public <P> AbstractSqlCommandPackage<T> between(P beginValue, P endValue) {
        this.checkField();
        if (beginValue == null || endValue == null) {
            return this.sqlCommandPackage;
        }
        this.sqlCommandPackage.getSqlBuilder().append(condition)
                .append("(").append(this.fieldName)
                .append(" between ? and ?)");
        this.sqlCommandPackage.getParameterList().add(beginValue);
        this.sqlCommandPackage.getParameterList().add(endValue);
        return this.sqlCommandPackage;
    }

    /**
     * 封装 数据段互相包含（开区间 位置相同）条件
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     * @author LiuHuiYu
     * Created DateTime 2022-12-01 10:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> inclusion(P minValue, P maxValue) {
        this.checkField2();
        if (minValue == null || maxValue == null) {
            return this.sqlCommandPackage;
        }
        this.sqlCommandPackage.getSqlBuilder().append(condition).append(" (");
        this.sqlCommandPackage.getSqlBuilder().append("((").append(minFieldName).append(" < ?").append(") and (").append(maxFieldName).append(" > ?").append("))");
        this.sqlCommandPackage.getParameterList().add(minValue);
        this.sqlCommandPackage.getParameterList().add(minValue);

        this.sqlCommandPackage.getSqlBuilder().append("or");
        this.sqlCommandPackage.getSqlBuilder().append("((").append(minFieldName).append(" < ?").append(") and (").append(maxFieldName).append(" > ?").append("))");
        this.sqlCommandPackage.getParameterList().add(maxValue);
        this.sqlCommandPackage.getParameterList().add(maxValue);

        this.sqlCommandPackage.getSqlBuilder().append("or");
        this.sqlCommandPackage.getSqlBuilder().append("((").append(minFieldName).append(" >= ?").append(") and (").append(maxFieldName).append(" <= ?").append("))");
        this.sqlCommandPackage.getSqlBuilder().append(")");
        this.sqlCommandPackage.getParameterList().add(minValue);
        this.sqlCommandPackage.getParameterList().add(maxValue);
        return this.sqlCommandPackage;
    }

    /**
     * 等于
     *
     * @param value 值
     * @author LiuHuiYu
     * Created DateTime 2023-02-23 23:51
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> eq(P value) {
        return this.generate("=", value);
    }

    /**
     * <> 比较
     *
     * @param value 值
     * @author LiuHuiYu
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> ne(P value) {
        return this.generate("<>", value);
    }

    /**
     * > 比较
     *
     * @param value 值
     * @author LiuHuiYu
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> gt(P value) {
        return this.generate(">", value);
    }

    /**
     * < 比较
     *
     * @param value 值
     * @author LiuHuiYu
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> lt(P value) {
        return this.generate("<", value);
    }

    /**
     * >= 比较
     *
     * @param value 值
     * @author LiuHuiYu
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> ge(P value) {
        return this.generate(">=", value);
    }

    /**
     * <= 比较
     *
     * @param value 值
     * @author LiuHuiYu
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public <P> AbstractSqlCommandPackage<T> le(P value) {
        return this.generate("<=", value);
    }

    /**
     * 为 null
     *
     * @author LiuHuiYu
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public AbstractSqlCommandPackage<T> isNull() {
        this.checkField();
        this.sqlCommandPackage.getSqlBuilder()
                .append(condition)
                .append("(").append(this.fieldName)
                .append(" is null )");
        return this.sqlCommandPackage;
    }

    /**
     * 为 null
     *
     * @author LiuHuiYu
     * Created DateTime 2023-03-25 9:23
     */
    @Override
    public AbstractSqlCommandPackage<T> isNotNull() {
        this.checkField();
        this.sqlCommandPackage.getSqlBuilder()
                .append(condition)
                .append("(").append(this.fieldName)
                .append(" is not null )");
        return this.sqlCommandPackage;
    }

    @Override
    public AbstractSqlCommandPackage<T> expression(String expression) {
        this.sqlCommandPackage.getSqlBuilder()
                .append(this.condition)
                .append("(")
                .append(expression)
                .append(")");
        return this.sqlCommandPackage;
    }

    private <P> AbstractSqlCommandPackage<T> generate(String operator, P value) {
        this.checkField();
        if (value != null) {
            this.sqlCommandPackage.getSqlBuilder()
                    .append(condition)
                    .append("(").append(this.fieldName)
                    .append(" ").append(operator)
                    .append(" ?").append(")");
            this.sqlCommandPackage.getParameterList().add(value);
        }
        return this.sqlCommandPackage;
    }

    /**
     * 子查询<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/8/19 15:37
     *
     * @param childSqlCommandPackage 查询语句
     * @return com.liuhuiyu.jpa.sql.AbstractSqlCommandPackage<T>
     */
    @Override
    public AbstractSqlCommandPackage<T> child(String operator, AbstractSqlCommandPackage<T> childSqlCommandPackage) {
        this.sqlCommandPackage.getParameterList().addAll(childSqlCommandPackage.getParameterList());
        this.sqlCommandPackage.getSqlBuilder()
                .append(condition)
                .append("(").append(this.fieldName)
                .append(" ")
                .append(operator)
                .append("(")
                .append(childSqlCommandPackage.getSqlBuilder().toString())
                .append("))");
        return this.sqlCommandPackage;
    }

    @Override
    public AbstractSqlCommandPackage<T> child(String operator, String childSql, List<Object> parameterList) {
        this.sqlCommandPackage.getParameterList().addAll(parameterList);
        this.sqlCommandPackage.getSqlBuilder()
                .append(condition)
                .append("(").append(this.fieldName)
                .append(" ")
                .append(operator)
                .append("(")
                .append(childSql)
                .append("))");
        return this.sqlCommandPackage;
    }
}
