package com.liuhuiyu.jpa.sql;

import java.util.List;

/**
 * 使用参数名方式填充查询条件
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-01-12 10:13
 */
@FunctionalInterface
public interface WhereFullByParameterList<T> {
    /**
     * 功能描述
     *
     * @param whereDto      条件
     * @param sqlBuilder    sql语句
     * @param parameterList 参数列表
     * @author LiuHuiYu
     * Created DateTime 2023-10-19 11:55
     */
    void full(T whereDto, StringBuilder sqlBuilder, List<Object> parameterList);
}
