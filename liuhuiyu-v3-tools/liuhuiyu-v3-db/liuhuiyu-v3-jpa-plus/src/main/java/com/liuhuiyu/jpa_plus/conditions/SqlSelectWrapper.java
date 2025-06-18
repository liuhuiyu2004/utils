package com.liuhuiyu.jpa_plus.conditions;

import com.liuhuiyu.jpa_plus.sql.SqlResolution;

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
    private final SqlOrderByWarapper orderBy;
    private final SqlGroupByWrapper groupBy;
    private final SqlConditionWrapper where;
    private final SqlConditionWrapper having;

    public SqlSelectWrapper(String selectSql) {
        this(selectSql, new SqlOrderByWarapper(), new SqlGroupByWrapper(), new SqlConditionWrapper(), new SqlConditionWrapper());
    }

    public SqlSelectWrapper(String selectSql, SqlOrderByWarapper orderBy, SqlGroupByWrapper groupBy, SqlConditionWrapper where, SqlConditionWrapper having) {
        this.selectSql = selectSql;
        this.orderBy = orderBy;
        this.groupBy = groupBy;
        this.where = where;
        this.having = having;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public SqlOrderByWarapper getOrderBy() {
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
        if (!where.getConditional().isEmpty()) {
            sql.append(" where ").append(where.getConditional());
        }
        if (!groupBy.getConditional().isEmpty()) {
            sql.append(" group by ").append(groupBy.getConditional());
        }
        if (!having.getConditional().isEmpty()) {
            sql.append(" having ").append(having.getConditional());
        }
        return sql.append(orderBy.getConditional()).toString();
    }
}
