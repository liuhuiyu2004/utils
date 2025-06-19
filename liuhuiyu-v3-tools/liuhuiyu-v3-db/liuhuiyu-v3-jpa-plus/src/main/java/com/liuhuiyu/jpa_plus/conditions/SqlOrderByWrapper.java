package com.liuhuiyu.jpa_plus.conditions;

import java.util.Optional;

/**
 * 功能<p>
 * Created on 2025/5/24 19:43
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlOrderByWrapper {
    StringBuilder sql = new StringBuilder();

    public Optional<String> getConditional() {
        if (sql.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(sql.toString());
    }

    public void addDesc(String fieldName) {
        if (!sql.isEmpty()) {
            sql.append(",");
        }
        sql.append(fieldName).append(" desc");
    }

    public void addAsc(String fieldName) {
        if (!sql.isEmpty()) {
            sql.append(",");
        }
        sql.append(fieldName).append(" asc");
    }
}
