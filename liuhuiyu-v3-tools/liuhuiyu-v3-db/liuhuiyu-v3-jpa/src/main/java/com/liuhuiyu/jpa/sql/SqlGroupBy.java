package com.liuhuiyu.jpa.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql Group by 功能<p>
 * Created on 2025/4/7 22:55
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlGroupBy {
    StringBuffer conditional = new StringBuffer();

    public StringBuffer getGroupBy() {
        if (!this.conditional.isEmpty()) {
            this.conditional.insert(0, " group by ");
        }
        return this.conditional;
    }

    public SqlGroupBy addGroup(String field) {
        if (!this.conditional.isEmpty()) {
            this.conditional.append(",");
        }
        this.conditional.append(field);
        return this;
    }
}
