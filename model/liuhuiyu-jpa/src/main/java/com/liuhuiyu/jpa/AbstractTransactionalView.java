package com.liuhuiyu.jpa;

import com.liuhuiyu.core.lang.function_interface.ObjectToT;
import com.liuhuiyu.jpa.util.SqlResolution;
import com.liuhuiyu.util.DataBaseUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-31 12:25
 */
public abstract class AbstractTransactionalView {
    EntityManager entityManager;

    public AbstractTransactionalView(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 返回第一行信息
     *
     * @param clazz        返回类型
     * @param sql          执行语句
     * @param parameterMap 参数表
     * @return java.util.Optional<T>
     * Created DateTime 2021-03-22 14:02
     */
    protected <T> Optional<T> getFirstResultT(Class<T> clazz, String sql, List<Object> parameterMap) {
        final Stream<T> resultListT = this.getResultListT(clazz, sql, parameterMap);
        return resultListT.findFirst();
    }

    /**
     * 返回第一行信息
     *
     * @param clazz        返回类型
     * @param sql          执行语句
     * @param parameterMap 参数表
     * @return java.util.Optional<T>
     * Created DateTime 2021-03-22 14:02
     */
    protected <T> List<T> getListT(Class<T> clazz, String sql, List<Object> parameterMap) {
        return this.getResultListT(clazz, sql, parameterMap).collect(Collectors.toList());
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
    protected <T> Stream<T> getResultListT(Class<T> clazz, String sql, List<Object> parameterList) {
        SqlResolution sqlResolution = new SqlResolution(sql);
        final Query nativeQuery = entityManager.createNativeQuery(sql);
        for (int i = 0, length = parameterList.size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, parameterList.get(i));
        }
        return (Stream<T>) nativeQuery.getResultList().stream().map(v -> DataBaseUtil.objToT(v, clazz, sqlResolution));
    }

    /**
     * 统计查询
     *
     * @param sql          sql语句
     * @param parameterMap 参数
     * @return java.lang.Long
     * Created DateTime 2022-02-15 16:54
     */
    protected Long selectCount(String sql, List<Object> parameterMap) {
        return this.getFirstResultT(Long.class, sql, parameterMap).orElse(0L);
    }
}
