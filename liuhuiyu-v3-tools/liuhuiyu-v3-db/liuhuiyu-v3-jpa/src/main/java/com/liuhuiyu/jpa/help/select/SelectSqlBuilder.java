package com.liuhuiyu.jpa.help.select;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/14 14:03
 */
public final class SelectSqlBuilder {
    private StringBuilder sqlWhere = new StringBuilder();
    private StringBuilder groupBy = new StringBuilder();
    private StringBuilder having = new StringBuilder();
    private StringBuilder orderBy = new StringBuilder();
    private List<Object> parameterList = new ArrayList<>();
    private String sqlBase = "";

    private SelectSqlBuilder(String sqlBase) {
        this.sqlBase = sqlBase;
    }

    public static SelectSqlBuilder createSelectSql(String sqlBase) {
        return new SelectSqlBuilder(sqlBase);
    }

    public SelectSqlBuilder withSqlWhere(String sqlWhere) {
        this.sqlWhere = new StringBuilder(sqlWhere);
        return this;
    }

    public SelectSqlBuilder withGroupBy(String groupBy) {
        this.groupBy = new StringBuilder(groupBy);
        return this;
    }

    public SelectSqlBuilder withHaving(String having) {
        this.having = new StringBuilder(having);
        return this;
    }

    public SelectSqlBuilder withOrderBy(String orderBy) {
        this.orderBy = new StringBuilder(orderBy);
        return this;
    }

    public SelectSqlBuilder withParameterList(List<Object> values) {
        this.parameterList = values;
        return this;
    }

    public SelectSqlBuilder withSqlBase(String sqlBase) {
        this.sqlBase = sqlBase;
        return this;
    }

    public SelectSql build() {
        SelectSql selectSql = new SelectSql(sqlBase);
        selectSql.setSqlWhere(sqlWhere);
        selectSql.setGroupBy(groupBy);
        selectSql.setHaving(having);
        selectSql.setOrderBy(orderBy);
        selectSql.setParameterList(parameterList);
        return selectSql;
    }
}
