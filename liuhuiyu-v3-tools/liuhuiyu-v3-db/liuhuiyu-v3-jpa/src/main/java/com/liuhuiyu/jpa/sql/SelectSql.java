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
    protected ConditionalFiltering sqlWhere;
    protected SqlGroupBy groupBy;
    protected ConditionalFiltering conditionalFiltering;
    protected SqlOrderBy orderBy;

    public SelectSql(String sqlBase) {
        this(sqlBase, new ConditionalFiltering(), new SqlGroupBy(), new ConditionalFiltering(), new SqlOrderBy());
    }

    public SelectSql(String sqlBase, ConditionalFiltering sqlWhere, SqlGroupBy groupBy, ConditionalFiltering conditionalFiltering, SqlOrderBy orderBy) {
        this.sqlBase = sqlBase;
        this.sqlWhere = sqlWhere;
        this.groupBy = groupBy;
        this.conditionalFiltering = conditionalFiltering;
        this.orderBy = orderBy;
    }

    public String getSqlBase() {
        return sqlBase;
    }

    public ConditionalFiltering getSqlWhere() {
        return sqlWhere;
    }

    public SqlGroupBy getGroupBy() {
        return groupBy;
    }

    public ConditionalFiltering getSqlHaving() {
        return conditionalFiltering;
    }

    public SqlOrderBy getOrderBy() {
        return orderBy;
    }
}
