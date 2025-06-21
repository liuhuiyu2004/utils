package com.liuhuiyu.jpa_plus.conditions;

import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * sql分组封装<p>
 * Created on 2025/5/11 20:44
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlGroupByWrapper {
    public static final String GROUP_BY_SQL = " GROUP BY ";

    public void add(String fieldName) {
        fieldNames.add(fieldName);
    }

    LinkedHashSet<String> fieldNames = new LinkedHashSet<>();

    public Optional<String> getConditional() {
        if (fieldNames.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(String.join(", ", fieldNames));
    }
}
