package com.liuhuiyu.jpa.sql;

import com.liuhuiyu.core.help.sql.SelectSql;

/**
 * 原生SQL封装
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 15:09
 */
public abstract class AbstractSqlCommandPackage<T, U extends Condition<T, U>> {
    protected T findDto;
    protected SelectSql selectSql;

    /**
     * SQL原生封装 构建函数
     *
     * @param findDto   查询条件
     * @param selectSql sql封装（包含参数）
     *                  Created DateTime 2022-11-20 8:27
     */
    public AbstractSqlCommandPackage(T findDto, SelectSql selectSql) {
        this.findDto = findDto;
        this.selectSql = selectSql;
    }

    /**
     * 执行命令封装
     * <p>
     * Created DateTime 2023-05-06 11:42
     */
    public void runCommandPackage() {
        this.commandPackage();
        if (this.conditionalDepth != 0) {
            throw new RuntimeException("条件层级未闭锁，请检查是否所有的 begin 都有 对应的 end。");
        }
    }

    /**
     * 命令封装
     * <p>
     * Created DateTime 2022-11-19 21:32
     */
    protected abstract void commandPackage();

    /**
     * 功能描述
     *
     * @param minFieldName 最大值字段
     * @param maxFieldName 最小值字段
     * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
     * Created DateTime 2023-03-29 9:25
     */
    protected abstract U conditionAnd(String minFieldName, String maxFieldName);

    /**
     * 条件生成
     *
     * @param fieldName 字段名称
     * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
     * Created DateTime 2023-02-23 23:56
     */
    protected abstract U conditionAnd(String fieldName);

    protected abstract U conditionOr(String minFieldName, String maxFieldName);

    /**
     * 条件生成
     *
     * @param fieldName 字段名称
     * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
     * Created DateTime 2023-02-23 23:56
     */
    protected abstract U conditionOr(String fieldName);

    protected abstract U condition(String minFieldName, String maxFieldName);

    /**
     * 条件生成
     *
     * @param fieldName 字段名称
     * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
     * Created DateTime 2023-02-23 23:56
     */
    protected abstract U condition(String fieldName);

    /**
     * 条件深度
     * Created DateTime 2023-10-19 16:18
     */
    protected int conditionalDepth = 0;

    public AbstractSqlCommandPackage<T, U> conditionalBegin(String condition) {
        this.conditionalDepth++;
        this.selectSql.getSqlWhere().append(condition).append("(");
        return this;
    }

    public AbstractSqlCommandPackage<T, U> conditionalEnd() {
        if (this.conditionalDepth <= 0) {
            throw new RuntimeException("条件层级不能为负");
        }
        this.conditionalDepth--;
        this.selectSql.getSqlWhere().append(")");
        return this;
    }

    public T getFindDto() {
        return findDto;
    }

    public SelectSql getSelectSql() {
        return selectSql;
    }

    public int getConditionalDepth() {
        return conditionalDepth;
    }
}
