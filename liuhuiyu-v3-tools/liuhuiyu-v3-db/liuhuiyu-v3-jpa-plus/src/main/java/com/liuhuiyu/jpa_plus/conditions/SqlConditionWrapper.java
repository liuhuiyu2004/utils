package com.liuhuiyu.jpa_plus.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public SqlConditionWrapper or(SqlConditionWrapper sqlConditionWrapper) {
        return s("or", sqlConditionWrapper);
    }

    public SqlConditionWrapper and(SqlConditionWrapper sqlConditionWrapper) {
        return s("and", sqlConditionWrapper);
    }

    private SqlConditionWrapper s(String condition, SqlConditionWrapper sqlConditionWrapper) {
        sqlConditionWrapper.getConditional().ifPresent(conditional -> {
            this.conditional.append(conditional);
            this.parameterList.addAll(sqlConditionWrapper.parameterList);
        });
        return this;
    }

    public class SqlConditionFullWrapper {
        /**
         * 关联操作符号（or / and）<p>
         */
        private final String condition;

        public SqlConditionFullWrapper(String condition) {
            this.condition = condition;
        }

        //region 字段值比较
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


        /**
         * 封装 数据段互相包含（闭区间 位置相同）条件
         *
         * @param beginField 开始字段
         * @param endField   结束字段
         * @param minValue   最小值
         * @param maxValue   最大值
         */
        public SqlConditionWrapper inclusion(String beginField, String endField, Object minValue, Object maxValue) {
            return this.inclusion(beginField, endField, minValue, maxValue, false);
        }

        /**
         * 封装 数据段互相包含（开区间 位置相同）条件
         *
         * @param beginField   开始字段
         * @param endField     结束字段
         * @param minValue     最小值
         * @param maxValue     最大值
         * @param openInterval 开区间
         */
        public SqlConditionWrapper inclusion(String beginField, String endField, Object minValue, Object maxValue, boolean openInterval) {
            if (minValue == null || maxValue == null) {
                return SqlConditionWrapper.this;
            }
            this.appendCondition();
            conditional
                    .append("((").append(beginField)
                    .append(openInterval ? " > " : " >= ")
                    .append("?)")
                    .append(" and (").append(endField)
                    .append(openInterval ? " < " : " <= ")
                    .append("?))");
            parameterList.add(minValue);
            parameterList.add(maxValue);
            return SqlConditionWrapper.this;
        }

        /**
         * like值
         *
         * @param value 值
         */
        public SqlConditionWrapper likeValue(String field, String value) {
            return likeValue(field, value, true, true, true);
        }

        /**
         * like值
         *
         * @param value 值
         * @param trim  去掉首尾空格
         * @param head  头部模糊匹配
         * @param tail  尾部模糊匹配
         */
        public SqlConditionWrapper likeValue(String field, String value, Boolean trim, Boolean head, Boolean tail) {
            if (value == null) {
                return SqlConditionWrapper.this;
            }
            this.appendCondition();
            conditional.append("(").append(field).append(" LIKE ").append("?").append(")");
            parameterList.add((head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : ""));
            return SqlConditionWrapper.this;
        }

        public SqlConditionWrapper in(String field, List<Object> parameterList) {
            if (parameterList == null || parameterList.stream().noneMatch(Objects::nonNull)) {
                return SqlConditionWrapper.this;
            }
            this.appendCondition();
            conditional.append("(").append(field).append(" in (");
            boolean notFirst = true;
            for (Object o : parameterList) {
                if (o != null) {
                    if (notFirst) {
                        conditional.append(",");
                    }
                    notFirst = false;
                    conditional.append("?");
                    parameterList.add(o);
                }
            }
            conditional.append("))");
            return SqlConditionWrapper.this;
        }
        //endregion

        //region 字段表达式计算
        public SqlConditionWrapper in(String field, SqlSelectWrapper select) {
            String sql = select.getSelectSql();
            this.appendCondition();
            conditional.append("(").append(field).append(" LIKE ").append(sql).append(")");
            parameterList.addAll(select.getParameterList());
            return SqlConditionWrapper.this;
        }
        //endregion

        //region 字段表达式
        //region 单目操作

        /**
         * 等于<p>
         * author liuhuiyu<p>
         * Created DateTime 2025/6/19 09:40
         *
         * @param field 字段名
         * @param field2 字段名2
         * @return com.liuhuiyu.jpa_plus.conditions.SqlConditionWrapper
         */
        public SqlConditionWrapper eqField(String field, String field2) {
            return this.appendConditionField(field, "=", field2);
        }

        /**
         * != 比较
         *
         * @param field 字段名
         * @param field2 字段名2
         */
        public <P> SqlConditionWrapper neField(String field, String field2) {
            return this.appendConditionField(field, " != ", field2);
        }

        /**
         * > 比较
         *
         * @param field 字段名
         * @param field2 字段名2
         */
        public <P> SqlConditionWrapper gtField(String field, String field2) {
            return this.appendConditionField(field, " > ", field2);
        }

        /**
         * &lt; 比较
         *
         * @param field 字段名
         * @param field2 字段名2
         */
        public <P> SqlConditionWrapper ltField(String field, String field2) {
            return this.appendConditionField(field, " < ", field2);
        }

        /**
         * >= 比较
         *
         * @param field 字段名
         * @param field2 字段名2
         */
        public <P> SqlConditionWrapper geField(String field, String field2) {
            return this.appendConditionField(field, " >= ", field2);
        }

        /**
         * &lt;= 比较
         *
         * @param field 字段名
         * @param field2 字段名2
         */
        public <P> SqlConditionWrapper leField(String field, String field2) {
            return this.appendConditionField(field, " <= ", field2);
        }

        private SqlConditionWrapper appendConditionField(String field, String operator, String field2) {
            appendCondition();
            conditional.append("(").append(field).append(" ").append(operator).append(" ").append(field2).append(")");
            return SqlConditionWrapper.this;
        }

        //endregion

        //endregion
    }
}
