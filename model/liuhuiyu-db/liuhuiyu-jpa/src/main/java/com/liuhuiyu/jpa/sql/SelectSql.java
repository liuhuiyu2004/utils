package com.liuhuiyu.jpa.sql;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 15:58
 */
public class SelectSql implements ISqlWhere {
    protected String sqlBase;
    protected StringBuilder sqlWhere;
    protected String sqlOrder;
    protected StringBuilder sqlEndAppend = new StringBuilder(0);
    protected List<Object> values = new ArrayList<>(0);

    public SelectSql(String sql) {
        this.sqlBase = sql;
        this.sqlWhere = new StringBuilder(" Where(1=1)");
        this.sqlOrder = "";
    }

    public SelectSql(String sql, Object[] values) {
        this.sqlBase = sql;
        this.sqlWhere = new StringBuilder(" Where(1=1)");
        this.sqlOrder = "";
        this.values = new ArrayList<>(Arrays.asList(values));
    }

    public SelectSql(String sql, String sqlWhere, String sqlOrder, Object[] values) {
        this.sqlBase = sql;
        this.sqlWhere = new StringBuilder(StringUtils.hasText(sqlWhere) ? sqlWhere : " Where(1=1)");
        this.sqlOrder = sqlOrder;
        this.values = new ArrayList<>(Arrays.asList(values));
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

    public String getSqlOrder() {
        return sqlOrder;
    }

    public void setSqlOrder(String sqlOrder) {
        this.sqlOrder = sqlOrder;
    }

    public StringBuilder getSqlEndAppend() {
        return sqlEndAppend;
    }

    public void setSqlEndAppend(StringBuilder sqlEndAppend) {
        this.sqlEndAppend = sqlEndAppend;
    }

    public String getSql() {
        return this.sqlBase + " " + this.sqlWhere + " " + this.sqlOrder + " " + this.sqlEndAppend;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public static final class Builder {
        private final String sqlBase;
        private StringBuilder sqlWhere = new StringBuilder(" Where(1=1)");
        private String sqlOrder = "";
        private StringBuilder sqlEndAppend = new StringBuilder(0);
        private List<Object> values = new ArrayList<>(0);


        private Builder(String sqlBase) {
            this.sqlBase = sqlBase;
        }

        public static Builder aSelectSql(String sqlBas) {
            return new Builder(sqlBas);
        }

        public Builder withSqlWhere(StringBuilder sqlWhere) {
            this.sqlWhere = sqlWhere;
            return this;
        }

        public Builder withSqlOrder(String sqlOrder) {
            this.sqlOrder = sqlOrder;
            return this;
        }

        public Builder withSqlEndAppend(StringBuilder sqlEndAppend) {
            this.sqlEndAppend = sqlEndAppend;
            return this;
        }

        public Builder withValues(List<Object> values) {
            this.values = values;
            return this;
        }

        public SelectSql build() {
            SelectSql selectSql = new SelectSql(null);
            selectSql.setSqlBase(sqlBase);
            selectSql.setSqlWhere(sqlWhere);
            selectSql.setSqlOrder(sqlOrder);
            selectSql.setSqlEndAppend(sqlEndAppend);
            selectSql.setValues(values);
            return selectSql;
        }
    }
}
