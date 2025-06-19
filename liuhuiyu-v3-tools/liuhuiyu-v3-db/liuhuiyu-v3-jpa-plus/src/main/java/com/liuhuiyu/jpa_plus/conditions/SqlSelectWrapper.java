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
        this(selectSql, new SqlOrderByWrapper(), new SqlGroupByWrapper(), new SqlConditionWrapper(), new SqlConditionWrapper());
    }

    public SqlSelectWrapper(String selectSql, SqlOrderByWrapper orderBy, SqlGroupByWrapper groupBy, SqlConditionWrapper where, SqlConditionWrapper having) {
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
            sql.append(" where ").append(conditional);
        });
        groupBy.getConditional().ifPresent(conditional -> {
            sql.append(" group by ").append(conditional);
        });
        having.getConditional().ifPresent(conditional -> {
            sql.append(" having ").append(conditional);
        });
        orderBy.getConditional().ifPresent(conditional -> {
            sql.append(" order by ").append(conditional);
        });
        return sql.toString();
    }
}
