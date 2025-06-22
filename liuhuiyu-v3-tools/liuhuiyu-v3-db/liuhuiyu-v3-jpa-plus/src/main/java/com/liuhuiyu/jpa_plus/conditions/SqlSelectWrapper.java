package com.liuhuiyu.jpa_plus.conditions;

import java.util.ArrayList;
import java.util.List;

/**
 * select封装功能<p>
 * Created on 2025/5/11 20:43
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlSelectWrapper {
    //sql基础语句
    private final String selectSql;
    private final SqlOrderByWrapper orderBy;
    private final SqlGroupByWrapper groupBy;
    private final SqlConditionWrapper where;
    private final SqlConditionWrapper having;

    public SqlSelectWrapper(String selectSql) {
        this(selectSql, new SqlConditionWrapper(), new SqlGroupByWrapper(), new SqlConditionWrapper(), new SqlOrderByWrapper());
    }

    public SqlSelectWrapper(String selectSql, SqlConditionWrapper where, SqlGroupByWrapper groupBy, SqlConditionWrapper having, SqlOrderByWrapper orderBy) {
        this.selectSql = selectSql;
        this.orderBy = orderBy;
        this.groupBy = groupBy;
        this.where = where;
        this.having = having;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public List<Object> getParameterList() {
        List<Object> resList = new ArrayList<>();
        resList.addAll(this.where.parameterList);
        resList.addAll(this.having.parameterList);
        return resList;
    }

    public SqlOrderByWrapper getOrderBy() {
        return orderBy;
    }

    public SqlGroupByWrapper getGroupBy() {
        return groupBy;
    }

    public SqlConditionWrapper getWhere() {
        return where;
    }

    public SqlConditionWrapper getHaving() {
        return having;
    }

    public String getSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(selectSql);
        where.getConditional().ifPresent(conditional -> {
            sql.append(SqlConditionWrapper.WHERE_SQL).append(conditional);
        });
        groupBy.getConditional().ifPresent(conditional -> {
            sql.append(SqlGroupByWrapper.GROUP_BY_SQL).append(conditional);
        });
        having.getConditional().ifPresent(conditional -> {
            sql.append(SqlConditionWrapper.HAVING_SQL).append(conditional);
        });
        orderBy.getConditional().ifPresent(conditional -> {
            sql.append(SqlOrderByWrapper.ORDER_BY_SQL).append(conditional);
        });
        return sql.toString();
    }

    public SqlSelectWrapper deepClone() {
            return new SqlSelectWrapper(this.selectSql, this.where.deepClone(), this.groupBy.deepClone(), this.having.deepClone(), this.orderBy.deepClone());
    }

    public static class Builder {
        private String selectSql;
        private SqlOrderByWrapper orderBy;
        private SqlGroupByWrapper groupBy;
        private SqlConditionWrapper where;
        private SqlConditionWrapper having;

        private Builder(String selectSql) {
            this.selectSql = selectSql;
        }

        public static Builder create(String selectSql) {
            return new Builder(selectSql);
        }
        public Builder setOrderBy(SqlOrderByWrapper orderBy) {
            this.orderBy = orderBy;
            return this;
        }
        public Builder setGroupBy(SqlGroupByWrapper groupBy) {
            this.groupBy = groupBy;
            return this;
        }
        public Builder setWhere(SqlConditionWrapper where) {
            this.where = where;
            return this;
        }
        public Builder setHaving(SqlConditionWrapper having) {
            this.having = having;
            return this;
        }
        public SqlSelectWrapper build() {
            return new SqlSelectWrapper(selectSql, where, groupBy, having, orderBy);
        }
    }
}
