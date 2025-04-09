package com.liuhuiyu.jpa.sql;

/**
 * 功能<p>
 * Created on 2025/4/7 20:37
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SelectSql {
    /**
     * 主语句（包括关联left join等）
     */
    protected final String sqlBase;
    protected SqlWhere sqlWhere;
    protected SqlGroupBy groupBy;
    protected SqlHaving sqlHaving;
    protected SqlOrderBy orderBy;

    public SelectSql(String sqlBase) {
        this(sqlBase, new SqlWhere(), new SqlGroupBy(), new SqlHaving(), new SqlOrderBy());
    }

    public SelectSql(String sqlBase, SqlWhere sqlWhere, SqlGroupBy groupBy, SqlHaving sqlHaving, SqlOrderBy orderBy) {
        this.sqlBase = sqlBase;
        this.sqlWhere = sqlWhere;
        this.groupBy = groupBy;
        this.sqlHaving = sqlHaving;
        this.orderBy = orderBy;
    }

    public String getSqlBase() {
        return sqlBase;
    }

    public SqlWhere getSqlWhere() {
        return sqlWhere;
    }

    public SqlGroupBy getGroupBy() {
        return groupBy;
    }

    public SqlHaving getSqlHaving() {
        return sqlHaving;
    }

    public SqlOrderBy getOrderBy() {
        return orderBy;
    }
}
