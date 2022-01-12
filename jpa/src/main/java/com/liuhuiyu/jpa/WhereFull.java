package com.liuhuiyu.jpa;

import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-01-12 10:13
 */
@FunctionalInterface
public interface WhereFull<T> {
    void full(T v, StringBuilder sqlBuilder, Map<String, Object> parameterMap);
}
