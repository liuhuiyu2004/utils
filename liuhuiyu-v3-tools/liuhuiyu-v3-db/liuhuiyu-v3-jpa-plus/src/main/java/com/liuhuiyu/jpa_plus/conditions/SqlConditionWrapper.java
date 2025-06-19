package com.liuhuiyu.jpa_plus.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 条件包装<p>
 * Created on 2025/5/11 20:40
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlConditionWrapper {

    final StringBuffer conditional = new StringBuffer();
    final List<Object> parameterList = new ArrayList<>();

    public Optional<String> getConditional() {
        if (conditional.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(conditional.toString());
    }

    public List<Object> getParameterList() {
        return parameterList;
    }

    public SqlConditionFullWrapper or() {
        return new SqlConditionFullWrapper("or");
    }

    public SqlConditionFullWrapper and() {
        return new SqlConditionFullWrapper("and");
    }

    public class SqlConditionFullWrapper {
        /**
         * 关联操作符号（or / and）<p>
         */
        private final String condition;

        public SqlConditionFullWrapper(String condition) {
            this.condition = condition;
        }
        //region 单目操作

        /**
         * 等于<p>
         * author liuhuiyu<p>
         * Created DateTime 2025/6/19 09:40
         *
         * @param field 字段名
         * @param value 值
         * @return com.liuhuiyu.jpa_plus.conditions.SqlConditionWrapper
         */
        public SqlConditionWrapper eq(String field, Object value) {
            return this.appendCondition(field, "=", value);
        }

        /**
         * != 比较
         *
         * @param value 值
         */
        public <P> SqlConditionWrapper ne(String field, Object value) {
            return this.appendCondition(field, " != ", value);
        }

        /**
         * > 比较
         *
         * @param value 值
         */
        public <P> SqlConditionWrapper gt(String field, Object value) {
            return this.appendCondition(field, " > ", value);
        }

        /**
         * &lt; 比较
         *
         * @param value 值
         * @param <P>   p
         */
        public <P> SqlConditionWrapper lt(String field, Object value) {
            return this.appendCondition(field, " < ", value);
        }

        /**
         * >= 比较
         *
         * @param value 值
         */
        public <P> SqlConditionWrapper ge(String field, Object value) {
            return this.appendCondition(field, " >= ", value);
        }

        /**
         * &lt;= 比较
         *
         * @param value 值
         */
        public <P> SqlConditionWrapper le(String field, Object value) {
            return this.appendCondition(field, " <= ", value);
        }

        /**
         * 为 null
         * <p>
         */
        public SqlConditionWrapper isNull(String field) {
            conditional.append(condition).append("(").append(field).append(" is null )");
            return SqlConditionWrapper.this;
        }

        /**
         * 为 null
         * <p>
         */
        public SqlConditionWrapper isNotNull(String field) {
            conditional.append(condition).append("(").append(field).append(" is not null )");
            return SqlConditionWrapper.this;
        }

        private SqlConditionWrapper appendCondition(String field, String operator, Object value) {
            if (value != null) {
                if (!conditional.isEmpty()) {
                    conditional.append(this.condition);
                }
                conditional.append("(").append(field).append(" ").append(operator).append(" ?)");
                parameterList.add(value);
            }

            return SqlConditionWrapper.this;
        }

        //endregion
        private void appendCondition() {
            if (!conditional.isEmpty()) {
                conditional.append(this.condition);
            }
        }

        /**
         * between闭区间
         *
         * @param beginValue 开始值
         * @param endValue   结束值
         */
        public SqlConditionWrapper between(String field, Object beginValue, Object endValue) {
            if (beginValue == null || endValue == null) {
                return SqlConditionWrapper.this;
            }
            this.appendCondition();
            conditional
                    .append("((").append(field)
                    .append(">= ?)AND(")
                    .append(field)
                    .append("<= ?))");
            parameterList.add(beginValue);
            parameterList.add(endValue);
            return SqlConditionWrapper.this;
        }
    }
}
