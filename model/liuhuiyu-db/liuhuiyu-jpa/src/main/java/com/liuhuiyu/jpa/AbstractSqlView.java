package com.liuhuiyu.jpa;

import com.liuhuiyu.jpa.help.SqlResolution;
import com.liuhuiyu.jpa.sql.SelectSql;
import com.liuhuiyu.jpa.util.DataBaseUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * sql查询视图
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/14 14:08
 */
public abstract class AbstractSqlView {
    /**
     * 实体管理器<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/9/14 14:09
     */
    EntityManager entityManager;

    public AbstractSqlView(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 返回数据流
     *
     * @param clazz         目标类型
     * @param sql           基本语句
     * @param parameterList 参数表
     * @return java.util.stream.Stream<T>
     * @author LiuHuiYu
     * Created DateTime 2023-12-31 14:32
     */
    @SuppressWarnings("all")
    protected <T> Stream<T> getResultListT(Class<T> clazz, SelectSql sql, SqlResolution resolution) {
        SqlResolution sqlResolution = resolution == null ? new SqlResolution(sql.getSql()) : resolution;
        final Query nativeQuery = entityManager.createNativeQuery(sql.getSql());
        for (int i = 0, length = sql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, sql.getParameterList().get(i));
        }
        return (Stream<T>) nativeQuery.getResultList().stream().map(v -> DataBaseUtil.objToT(v, clazz, sqlResolution));
    }

    protected <T> Stream<T> getResultListT(Class<T> clazz, SelectSql sql) {
        return getResultListT(clazz, sql, null);
    }

    /**
     * 返回第一行信息
     *
     * @param clazz 返回类型
     * @param sql   执行语句
     * @return java.util.Optional<T>
     * Created DateTime 2021-03-22 14:02
     */
    protected <T> Optional<T> getFirstResultT(Class<T> clazz, SelectSql sql) {
        final Stream<T> resultListT = this.getResultListT(clazz, sql);
        return resultListT.findFirst();
    }

//    /**
//     * 统计查询
//     *
//     * @param sql sql
//     * @return java.lang.Long
//     * Created DateTime 2022-02-15 16:54
//     */
//    @SuppressWarnings("all")
//    protected Long selectCount(SelectSql sql) {
//        final Query nativeQuery = entityManager.createNativeQuery(sql.getSql());
//        for (int i = 0, length = sql.getParameterList().size(); i < length; i++) {
//            nativeQuery.setParameter(i + 1, sql.getParameterList().get(i));
//        }
//        final Object singleResult = nativeQuery.getSingleResult();
//        return Long.parseLong(singleResult.toString());
//    }

    /**
     * 统计查询
     *
     * @param sql sql语句
     * @return java.lang.Long
     * Created DateTime 2022-02-15 16:54
     */
    @SuppressWarnings("all")
    protected Long selectCount(SelectSql sql) {
        final Query nativeQuery = entityManager.createNativeQuery(sql.getSql());
        for (int i = 0, length = sql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, sql.getParameterList().get(i));
        }
        final Object singleResult = nativeQuery.getSingleResult();
        return Long.parseLong(singleResult.toString());
    }
}
