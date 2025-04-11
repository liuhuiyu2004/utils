package com.liuhuiyu.jpa.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql Having 功能<p>
 * Created on 2025/4/7 20:46
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class ConditionalFiltering {
    StringBuffer conditional = new StringBuffer();
    List<Object> parameterList = new ArrayList<>();

    public StringBuffer getConditional() {
        return this.conditional;
    }

    public List<Object> getParameterList() {
        return this.parameterList;
    }

    public Condition and(String field) {
        return new Condition(this, field, conditional.isEmpty() ? "" : "and");
    }

    public Condition or(String field) {
        return new Condition(this, field, conditional.isEmpty() ? "" : "or");
    }

    public ConditionalFiltering or(ConditionalFiltering conditionalFiltering) {
        if (!conditionalFiltering.conditional.isEmpty()) {
            this.conditional.append(conditional.isEmpty() ? "" : "or")
                    .append(conditionalFiltering.getConditional());
            this.parameterList.addAll(conditionalFiltering.getParameterList());
        }
        return this;
    }

    public ConditionalFiltering and(ConditionalFiltering conditionalFiltering) {
        if (!conditionalFiltering.conditional.isEmpty()) {
            this.conditional.append(conditional.isEmpty() ? "" : "and")
                    .append(conditionalFiltering.getConditional());
            this.parameterList.addAll(conditionalFiltering.getParameterList());
        }
        return this;
    }

    public ConditionalFiltering deepClone() {
        ConditionalFiltering conditionalFiltering = new ConditionalFiltering();
        conditionalFiltering.conditional = new StringBuffer(this.conditional.toString());
        conditionalFiltering.parameterList = new ArrayList<>(this.parameterList);
        return conditionalFiltering;
    }
}
