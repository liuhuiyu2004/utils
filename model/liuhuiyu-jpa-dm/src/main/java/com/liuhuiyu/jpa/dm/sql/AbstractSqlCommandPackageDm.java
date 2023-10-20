package com.liuhuiyu.jpa.dm.sql;

import com.liuhuiyu.jpa.sql.AbstractSqlCommandPackage;
import com.liuhuiyu.jpa.sql.Condition;

import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-20 9:43
 */
public abstract class AbstractSqlCommandPackageDm<T> extends AbstractSqlCommandPackage<T> {
    /**
     * SQL原生封装 构建函数
     *
     * @param findDto       查询条件
     * @param sqlBuilder    sqlBuilder
     * @param parameterList 参数
     * @author LiuHuiYu
     * Created DateTime 2022-11-20 8:27
     */
    public AbstractSqlCommandPackageDm(T findDto, StringBuilder sqlBuilder, List<Object> parameterList) {
        super(findDto, sqlBuilder, parameterList);
    }

    @Override
    protected Condition<T> conditionAnd(String minFieldName, String maxFieldName) {
        DmCondition<T> resData = new DmCondition<>(this);
        resData.setCondition(" AND ");
        resData.setMinFieldName(minFieldName);
        resData.setMaxFieldName(maxFieldName);
        return resData;
    }

    @Override
    protected Condition<T> conditionAnd(String fieldName) {
        DmCondition<T> resData = new DmCondition<>(this);
        resData.setCondition(" AND ");
        resData.setFieldName(fieldName);
        return resData;
    }

    @Override
    protected Condition<T> conditionOr(String minFieldName, String maxFieldName) {
        DmCondition<T> resData = new DmCondition<>(this);
        resData.setCondition(" OR ");
        resData.setMinFieldName(minFieldName);
        resData.setMaxFieldName(maxFieldName);
        return resData;
    }

    @Override
    protected Condition<T> conditionOr(String fieldName) {
        DmCondition<T> resData = new DmCondition<>(this);
        resData.setCondition(" OR ");
        resData.setFieldName(fieldName);
        return resData;
    }

    @Override
    protected Condition<T> condition(String minFieldName, String maxFieldName) {
        DmCondition<T> resData = new DmCondition<>(this);
        resData.setCondition(" ");
        resData.setMinFieldName(minFieldName);
        resData.setMaxFieldName(maxFieldName);
        return resData;
    }

    /**
     * 条件生成
     *
     * @param fieldName 字段名称
     * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
     * @author LiuHuiYu
     * Created DateTime 2023-02-23 23:56
     */
    @Override
    protected Condition<T> condition(String fieldName) {
        DmCondition<T> resData = new DmCondition<>(this);
        resData.setCondition(" ");
        resData.setFieldName(fieldName);
        return resData;
    }
}
