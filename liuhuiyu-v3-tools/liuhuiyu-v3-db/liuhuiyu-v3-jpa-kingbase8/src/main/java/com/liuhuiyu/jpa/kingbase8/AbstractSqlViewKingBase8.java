package com.liuhuiyu.jpa.kingbase8;

import com.liuhuiyu.core.help.sql.SelectSql;
import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.AbstractSqlView;
import com.liuhuiyu.jpa.help.SqlResolution;
import com.liuhuiyu.jpa.kingbase8.util.Kingbase8DaoUtil;
import com.liuhuiyu.jpa.sql.WhereFullByParameterList;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/12/31 11:02
 */
public abstract class AbstractSqlViewKingBase8 <returnT extends IComputedValueFilling, findT> extends AbstractSqlView<returnT, findT> {
    public static final String SPACE = " ";

    public AbstractSqlViewKingBase8(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * 获取分页列表数据
     *
     * @param clazz     获取数据后的转换
     * @param t         获取条件
     * @param sql       基础查询语句
     * @param fullWhere 填充查询条件
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return java.util.List<R>
     * Created DateTime 2022-04-25 10:29
     */
    protected <T, R> List<R> pageList(Class<R> clazz, T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        SqlResolution sqlResolution = new SqlResolution(sql.getSql());
        Assert.assertTrue(t instanceof IPaging, "查询条件不包含分页信息。");
        IPaging iPaging = (IPaging) t;
        fullWhere.full(t, sql);
        StringBuilder sqlBuilder = new StringBuilder(sql.getSql());
        Kingbase8DaoUtil.paginationOracleSql(sqlBuilder, iPaging.getPaging());
        SelectSql sql1 = new SelectSql(sqlBuilder.toString());
        sql1.setParameterList(sql.getParameterList());
        return super.getResultListT(clazz, sql1, sqlResolution).collect(Collectors.toList());
    }
}
