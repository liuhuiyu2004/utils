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
    protected String groupBy = "";
    protected SqlHaving sqlHaving;
    protected String orderBy = "";

    public SelectSql(String sqlBase) {
        this.sqlBase = sqlBase;
        this.sqlWhere = new SqlWhere();
        this.sqlHaving = new SqlHaving();
    }
}
