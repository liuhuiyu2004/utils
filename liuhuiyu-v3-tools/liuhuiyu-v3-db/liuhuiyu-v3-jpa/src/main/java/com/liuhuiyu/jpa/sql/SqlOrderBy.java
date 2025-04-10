package com.liuhuiyu.jpa.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql order功能<p>
 * Created on 2025/4/7 22:56
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlOrderBy {
    StringBuffer conditional = new StringBuffer();

    public String getOrder() {
        if (!this.conditional.isEmpty()) {
            this.conditional.insert(0, " order by");
        }
        return this.conditional.toString();
    }

    public SqlOrderBy asc(String field) {
        if (!this.conditional.isEmpty()) {
            this.conditional.append(",");
        }
        this.conditional.append(" ").append(field).append(" asc");
        return this;
    }

    public SqlOrderBy desc(String field) {
        if (!this.conditional.isEmpty()) {
            this.conditional.append(",");
        }
        this.conditional.append(" ").append(field).append(" desc");
        return this;
    }
}
