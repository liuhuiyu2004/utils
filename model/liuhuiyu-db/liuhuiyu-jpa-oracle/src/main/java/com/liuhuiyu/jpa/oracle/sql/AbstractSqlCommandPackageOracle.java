package com.liuhuiyu.jpa.oracle.sql;

import com.liuhuiyu.core.help.sql.SelectSql;
import com.liuhuiyu.jpa.sql.AbstractSqlCommandPackage;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-20 9:43
 */
public abstract class AbstractSqlCommandPackageOracle<T> extends AbstractSqlCommandPackage<T, OracleCondition<T>> {
    /**
     * SQL原生封装 构建函数
     *
     * @param findDto   查询条件
     * @param selectSql selectSql
     * @author LiuHuiYu
     * Created DateTime 2022-11-20 8:27
     */
    public AbstractSqlCommandPackageOracle(T findDto, SelectSql selectSql) {
        super(findDto, selectSql);
    }

    @Override
    protected OracleCondition<T> conditionAnd(String minFieldName, String maxFieldName) {
        OracleCondition<T> resData = new OracleCondition<>(this);
        resData.setCondition(" AND ");
        resData.setMinFieldName(minFieldName);
        resData.setMaxFieldName(maxFieldName);
        return resData;
    }

    @Override
    protected OracleCondition<T> conditionAnd(String fieldName) {
        OracleCondition<T> resData = new OracleCondition<>(this);
        resData.setCondition(" AND ");
        resData.setFieldName(fieldName);
        return resData;
    }

    @Override
    protected OracleCondition<T> conditionOr(String minFieldName, String maxFieldName) {
        OracleCondition<T> resData = new OracleCondition<>(this);
        resData.setCondition(" OR ");
        resData.setMinFieldName(minFieldName);
        resData.setMaxFieldName(maxFieldName);
        return resData;
    }

    @Override
    protected OracleCondition<T> conditionOr(String fieldName) {
        OracleCondition<T> resData = new OracleCondition<>(this);
        resData.setCondition(" OR ");
        resData.setFieldName(fieldName);
        return resData;
    }

    @Override
    protected OracleCondition<T> condition(String minFieldName, String maxFieldName) {
        OracleCondition<T> resData = new OracleCondition<>(this);
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
    protected OracleCondition<T> condition(String fieldName) {
        OracleCondition<T> resData = new OracleCondition<>(this);
        resData.setCondition(" ");
        resData.setFieldName(fieldName);
        return resData;
    }
}
