package com.liuhuiyu.jpa.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql where 功能<p>
 * Created on 2025/4/7 20:43
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlWhere implements ConditionalFiltering {
    StringBuffer conditional = new StringBuffer();
    List<Object> parameterList = new ArrayList<>();

    @Override
    public StringBuffer getConditional() {
        return null;
    }

    @Override
    public List<Object> getParameterList() {
        return List.of();
    }

    public Condition and(String field) {
        return new Condition(this, field, conditional.isEmpty() ? "" : "and");
    }

    public Condition or(String field) {
        return new Condition(this, field, conditional.isEmpty() ? "" : "or");
    }
}
