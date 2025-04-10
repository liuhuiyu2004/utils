package com.liuhuiyu.jpa.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * sql语句组合<p>
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
    protected ConditionalFiltering having;
    protected SqlOrderBy orderBy;

    public SelectSql(String sqlBase) {
        this(sqlBase, new ConditionalFiltering(), new SqlGroupBy(), new ConditionalFiltering(), new SqlOrderBy());
    }

    public SelectSql(String sqlBase, ConditionalFiltering sqlWhere, SqlGroupBy groupBy, ConditionalFiltering having, SqlOrderBy orderBy) {
        this.sqlBase = sqlBase;
        this.sqlWhere = sqlWhere;
        this.groupBy = groupBy;
        this.having = having;
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
        return having;
    }

    public SqlOrderBy getOrderBy() {
        return orderBy;
    }

    public String getSql() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.sqlBase);
        if (!this.sqlWhere.getConditional().isEmpty()) {
            sb.append(" where ").append(this.sqlWhere.getConditional());
        }
        sb.append(this.groupBy.getGroupBy());
        if (!this.having.getConditional().isEmpty()) {
            sb.append(" having ").append(this.having.getConditional());
        }
        sb.append(this.orderBy.getOrder());
        return sb.toString();

    }

    public List<Object> getParameterList() {
        List<Object> parameterList = new ArrayList<>();
        if (!this.sqlWhere.getConditional().isEmpty()) {
            parameterList.addAll(this.sqlWhere.getParameterList());
        }
        if (!this.having.getConditional().isEmpty()) {
            parameterList.addAll(this.having.getParameterList());
        }
        return parameterList;
    }
}
