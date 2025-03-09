package com.liuhuiyu.jpa.help.select2;

import java.util.List;

/**
 * sql表达式需要承载表达式的基础
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/3/1 16:55
 */
public class SqlExpression {
    private final String field1;
    private final String field2;
    private final ExpressionZt expressionZt;

    public SqlExpression(ExpressionZt expressionZt, String field) {
        this.field1 = field;
        this.field2 = null;
        this.expressionZt = expressionZt;
    }

    public SqlExpression(ExpressionZt expressionZt, String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
        this.expressionZt = expressionZt;
    }

    //region 输出
    // 获取表达式字符串
    public String getExpression() {
        return this.expressionZt.getSqlZtBuilder().toString();
    }

    // 获取参数列表
    public List<Object> getParameters() {
        return this.expressionZt.getSqlZtParameters();
    }
    //endregion

    //region 参数条件
    // 添加比较条件
    public ExpressionZt eq(Object value) {
        appendCondition("=", value);
        return this.expressionZt;
    }

    public SqlExpression ne(Object value) {
        appendCondition("!=", value);
        return this;
    }

    public SqlExpression gt(Object value) {
        appendCondition(">", value);
        return this;
    }

    public SqlExpression lt(Object value) {
        appendCondition("<", value);
        return this;
    }

    public SqlExpression gte(Object value) {
        appendCondition(">=", value);
        return this;
    }

    public SqlExpression lte(Object value) {
        appendCondition("<=", value);
        return this;
    }

    // 添加范围条件
    public SqlExpression between(Object min, Object max) {
        this.expressionZt.getSqlZtBuilder().append(" BETWEEN ? AND ?");
        this.expressionZt.getSqlZtBuilder().append("((").append(field1).append(">=?)AND").append(field1).append("<=?)");
        this.expressionZt.getSqlZtParameters().add(min);
        this.expressionZt.getSqlZtParameters().add(max);
        return this;
    }

    // 添加函数调用
    public ExpressionZt function(String functionName, Object... args) {
        this.expressionZt.getSqlZtBuilder().append(functionName).append("(");
        for (int i = 0; i < args.length; i++) {
            this.expressionZt.getSqlZtBuilder().append("?");
            this.expressionZt.getSqlZtParameters().add(args[i]);
            if (i < args.length - 1) {
                this.expressionZt.getSqlZtBuilder().append(", ");
            }
        }
        this.expressionZt.getSqlZtBuilder().append(")");
        return this.expressionZt;
    }

    // 私有方法：添加条件
    private void appendCondition(String operator, Object value) {
        this.expressionZt.getSqlZtBuilder().append(this.expressionZt.getConnectors()).append("(").append(field1).append(" ").append(operator).append(" ?)");
        this.expressionZt.getSqlZtParameters().add(value);
    }
    //endregion

    //region sql条件

    public ExpressionZt eqSql(SelectSql selectSql) {
        this.appendSql("=", selectSql);
        return expressionZt;
    }

    public ExpressionZt inSql(SelectSql selectSql) {
        this.appendSql("in", selectSql);
        return expressionZt;
    }

    private void appendSql(String operator, SelectSql selectSql) {
        expressionZt.getSqlZtBuilder().append("(")
                .append(field1).append(" ").append(operator)
                .append(" (")
                .append(selectSql.getSql())
                .append(")");
        expressionZt.getSqlZtParameters().addAll(selectSql.getParameters());
    }
    //endregion

    //region 字段比较条件

    //endregion
}
