package com.liuhuiyu.db.demo.db;

import com.liuhuiyu.core.help.sql.SelectSql;
//import com.liuhuiyu.jpa.kingbase8.sql.AbstractSqlCommandPackageKingbase8;
//import com.liuhuiyu.jpa.oracle.sql.AbstractSqlCommandPackageOracle;
import com.liuhuiyu.jpa.dm.sql.AbstractSqlCommandPackageDm;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2024/11/29 11:52
 */
//public abstract class AbstractSqlCommandPackageDb<T> extends AbstractSqlCommandPackageKingbase8<T> {
//public abstract class AbstractSqlCommandPackageDb<T> extends AbstractSqlCommandPackageOracle<T> {
public abstract class AbstractSqlCommandPackageDb<T> extends AbstractSqlCommandPackageDm<T> {
    /**
     * SQL原生封装 构建函数
     *
     * @param findDto   查询条件
     * @param selectSql selectSql
     * @author LiuHuiYu
     * Created DateTime 2022-11-20 8:27
     */
    public AbstractSqlCommandPackageDb(T findDto, SelectSql selectSql) {
        super(findDto, selectSql);
    }
}

