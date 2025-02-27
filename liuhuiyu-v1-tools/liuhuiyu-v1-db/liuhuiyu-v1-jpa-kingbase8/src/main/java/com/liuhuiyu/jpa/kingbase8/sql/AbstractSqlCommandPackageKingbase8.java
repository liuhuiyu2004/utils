package com.liuhuiyu.jpa.kingbase8.sql;

import com.liuhuiyu.core.help.sql.SelectSql;
import com.liuhuiyu.jpa.sql.AbstractSqlCommandPackage;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-20 9:43
 */
public abstract class AbstractSqlCommandPackageKingbase8<T> extends AbstractSqlCommandPackage<T, Kingbase8Condition<T>> {
    /**
     * SQL原生封装 构建函数
     *
     * @param findDto   查询条件
     * @param selectSql selectSql

     * Created DateTime 2022-11-20 8:27
     */
    public AbstractSqlCommandPackageKingbase8(T findDto, SelectSql selectSql) {
        super(findDto, selectSql);
    }

    @Override
    protected Kingbase8Condition<T> conditionAnd(String minFieldName, String maxFieldName) {
        Kingbase8Condition<T> resData = new Kingbase8Condition<>(this);
        resData.setCondition(" AND ");
        resData.setMinFieldName(minFieldName);
        resData.setMaxFieldName(maxFieldName);
        return resData;
    }

    @Override
    protected Kingbase8Condition<T> conditionAnd(String fieldName) {
        Kingbase8Condition<T> resData = new Kingbase8Condition<>(this);
        resData.setCondition(" AND ");
        resData.setFieldName(fieldName);
        return resData;
    }

    @Override
    protected Kingbase8Condition<T> conditionOr(String minFieldName, String maxFieldName) {
        Kingbase8Condition<T> resData = new Kingbase8Condition<>(this);
        resData.setCondition(" OR ");
        resData.setMinFieldName(minFieldName);
        resData.setMaxFieldName(maxFieldName);
        return resData;
    }

    @Override
    protected Kingbase8Condition<T> conditionOr(String fieldName) {
        Kingbase8Condition<T> resData = new Kingbase8Condition<>(this);
        resData.setCondition(" OR ");
        resData.setFieldName(fieldName);
        return resData;
    }

    @Override
    protected Kingbase8Condition<T> condition(String minFieldName, String maxFieldName) {
        Kingbase8Condition<T> resData = new Kingbase8Condition<>(this);
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

     * Created DateTime 2023-02-23 23:56
     */
    @Override
    protected Kingbase8Condition<T> condition(String fieldName) {
        Kingbase8Condition<T> resData = new Kingbase8Condition<>(this);
        resData.setCondition(" ");
        resData.setFieldName(fieldName);
        return resData;
    }
}
