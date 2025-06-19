package com.liuhuiyu.jpa_plus.conditions;

import java.util.List;
import java.util.Optional;

/**
 * 功能<p>
 * Created on 2025/6/19 08:39
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlConditionAssociationWrapper {
    StringBuilder sql = new StringBuilder();
    public Optional<String> getConditional() {
        if (sql.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(sql.toString());
    }
}
