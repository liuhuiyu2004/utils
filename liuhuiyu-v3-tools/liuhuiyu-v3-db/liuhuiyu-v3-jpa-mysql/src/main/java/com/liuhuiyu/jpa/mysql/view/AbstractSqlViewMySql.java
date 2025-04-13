package com.liuhuiyu.jpa.mysql.view;

import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.sql.SelectSql;
import com.liuhuiyu.jpa.sql.SqlResolution;
import com.liuhuiyu.jpa.util.DataBaseUtil;
import com.liuhuiyu.jpa.view.AbstractSqlView;
import com.liuhuiyu.jpa.view.WhereFullByParameterList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能<p>
 * Created on 2025/4/13 21:13
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public abstract class AbstractSqlViewMySql<returnT extends IComputedValueFilling, findT> extends AbstractSqlView<returnT, findT> {
    public AbstractSqlViewMySql(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * 获取分页列表数据
     *
     * @param clazz     获取数据后的转换
     * @param findWhere 获取条件
     * @param selectSql 基础查询语句
     * @param fullWhere 填充查询条件
     * @return java.util.List<returnT>
     * Created DateTime 2022-04-25 10:29
     */
    @SuppressWarnings("all")
    protected List<returnT> pageList(Class<returnT> clazz, findT findWhere, SelectSql selectSql, WhereFullByParameterList<findT> fullWhere) {
        //填充sql
        fullWhere.full(findWhere, selectSql);
        IPaging iPaging = (IPaging) findWhere;
        String sqlCommand = selectSql.getSql() + " limit " + iPaging.getPaging().beginRowNo() + ", " + iPaging.getPaging().getPageSize();
        SqlResolution sqlResolution = new SqlResolution(sqlCommand);
        final Query nativeQuery = entityManager.createNativeQuery(sqlCommand);
        for (int i = 0, length = selectSql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, selectSql.getParameterList().get(i));
        }
        List<returnT> resList = new ArrayList<>();
        for (Object v : nativeQuery.getResultList()) {
            resList.add(DataBaseUtil.objToT(v, clazz, sqlResolution));
        }
        return resList;
    }
}
