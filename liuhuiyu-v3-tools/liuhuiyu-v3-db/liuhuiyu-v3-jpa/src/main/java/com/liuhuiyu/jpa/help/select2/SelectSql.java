package com.liuhuiyu.jpa.help.select2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/3/2 11:17
 */
public class SelectSql {
    protected String sqlBase = "";
    protected SqlWhere sqlWhere = new SqlWhere();

    public SelectSql(String sqlBase, SqlWhere sqlWhere) {
        this.sqlBase = sqlBase;
        this.sqlWhere = sqlWhere;
    }

    public String getSqlBase() {
        return sqlBase;
    }

    public void setSqlBase(String sqlBase) {
        this.sqlBase = sqlBase;
    }

    public SqlWhere getSqlWhere() {
        return sqlWhere;
    }

    public void setSqlWhere(SqlWhere sqlWhere) {
        this.sqlWhere = sqlWhere;
    }

    public String getSql() {
        return this.sqlBase + " " +
                this.sqlWhere.getSqlZtBuilder() + " ";
    }

    public List<Object> getParameters() {
        return new ArrayList<>(this.sqlWhere.getSqlZtParameters());
    }

    public static class Builder {
        final String sql;
        SqlWhere sqlWhere = new SqlWhere();

        private Builder(String sql) {
            this.sql = sql;
        }

        public static Builder create(String sql) {
            return new Builder(sql);
        }

        public Builder withSqlWhere(String whereExpression) {
            this.sqlWhere = new SqlWhere(whereExpression);
            return this;
        }

        public SelectSql build() {
            return new SelectSql(this.sql, this.sqlWhere);
        }
    }
}
