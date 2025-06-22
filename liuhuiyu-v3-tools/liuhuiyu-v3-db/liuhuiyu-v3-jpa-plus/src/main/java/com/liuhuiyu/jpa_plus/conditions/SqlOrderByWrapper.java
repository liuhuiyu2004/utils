package com.liuhuiyu.jpa_plus.conditions;

import java.util.LinkedHashSet;
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
    public static final String ORDER_BY_SQL = " ORDER BY ";
    LinkedHashSet<String> fieldNames = new LinkedHashSet<>();

    public Optional<String> getConditional() {
        if (fieldNames.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(String.join(", ", fieldNames));
    }

    public void addDesc(String fieldName) {
        fieldNames.add(fieldName + " desc");
    }

    public void addAsc(String fieldName) {
        fieldNames.add(fieldName + " asc");
    }

    public SqlOrderByWrapper deepClone() {
        SqlOrderByWrapper sqlOrderByWrapper = new SqlOrderByWrapper();
        sqlOrderByWrapper.fieldNames.addAll(this.fieldNames);
        return sqlOrderByWrapper;
    }
}
