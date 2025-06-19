package com.liuhuiyu.jpa_plus.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 条件包装<p>
 * Created on 2025/5/11 20:40
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class SqlConditionWrapper {

    final StringBuffer conditional = new StringBuffer();
    final List<Object> parameterList = new ArrayList<>();

    public Optional<String> getConditional() {
        if (conditional.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(conditional.toString());
    }

    public List<Object> getParameterList() {
        return parameterList;
    }

    public void or(){

    }
    public void and(){

    }
}
