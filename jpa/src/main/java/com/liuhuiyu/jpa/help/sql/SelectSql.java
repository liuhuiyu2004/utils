package com.liuhuiyu.jpa.help.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/14 13:57
 */
public class SelectSql implements Serializable {

    protected String sqlBase = "";
    protected StringBuilder sqlWhere = new StringBuilder();
    protected StringBuilder groupBy = new StringBuilder();
    protected StringBuilder having = new StringBuilder();
    protected StringBuilder orderBy = new StringBuilder();
    protected List<Object> parameterList = new ArrayList<>(0);


    public SelectSql(String sqlBase) {
        this.sqlBase = sqlBase;
    }

    public SelectSql(String sqlBase, StringBuilder sqlWhere, StringBuilder groupBy, StringBuilder having, StringBuilder orderBy, List<Object> parameterList) {
        this.sqlBase = sqlBase;
        this.sqlWhere = sqlWhere;
        this.groupBy = groupBy;
        this.having = having;
        this.orderBy = orderBy;
        this.parameterList = parameterList;
    }

    public String getSqlBase() {
        return sqlBase;
    }

    public void setSqlBase(String sqlBase) {
        this.sqlBase = sqlBase;
    }

    public StringBuilder getSqlWhere() {
        return sqlWhere;
    }

    public void setSqlWhere(StringBuilder sqlWhere) {
        this.sqlWhere = sqlWhere;
    }

    public StringBuilder getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(StringBuilder groupBy) {
        this.groupBy = groupBy;
    }

    public StringBuilder getHaving() {
        return having;
    }

    public void setHaving(StringBuilder having) {
        this.having = having;
    }

    public StringBuilder getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(StringBuilder orderBy) {
        this.orderBy = orderBy;
    }

    public List<Object> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Object> parameterList) {
        this.parameterList = parameterList;
    }

    public String getSql() {
        return this.sqlBase + " " +
                this.sqlWhere + " " +
                this.groupBy + " " +
                this.having + " " +
                orderBy;
    }
}
