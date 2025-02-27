package com.liuhuiyu.jpa;

import com.liuhuiyu.core.help.sql.SelectSql;
import com.liuhuiyu.core.help.sql.SelectSqlBuilder;
import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.help.SqlResolution;
import com.liuhuiyu.jpa.sql.WhereFullByParameterList;
import com.liuhuiyu.jpa.util.DataBaseUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * sql查询视图
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/14 14:08
 */
public abstract class AbstractSqlView<returnT extends IComputedValueFilling, findT> {
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
     * @param resolution    sql解析
     * @return java.util.stream.Stream&lt;T>

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

    /**
     * 统计查询
     *
     * @param sql sql语句
     * @return java.lang.Long
     * Created DateTime 2022-02-15 16:54
     */
    @SuppressWarnings("all")
    protected Long selectCount(SelectSql sql) {
        String sqlCommand = sql.getSql();
        //对于聚合sql语句，需要用子查询解决
        if (sql.getGroupBy().length() != 0) {
            sqlCommand = "select count(1) from (" + sqlCommand + ")";
        }
        final Query nativeQuery = entityManager.createNativeQuery(sqlCommand);
        for (int i = 0, length = sql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, sql.getParameterList().get(i));
        }
        final Object singleResult = nativeQuery.getSingleResult();
        return Long.parseLong(singleResult.toString());
    }

    protected <T> Long count(T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        fullWhere.full(t, sql);
        return this.selectCount(sql);
    }

    /**
     * 获取列表数据
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
    protected <T, R> List<R> list(Class<R> clazz, T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        fullWhere.full(t, sql);
        return this.getResultListT(clazz, sql).collect(Collectors.toList());
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
    protected abstract <T, R> List<R> pageList(Class<R> clazz, T t, SelectSql sql, WhereFullByParameterList<T> fullWhere);

    protected <T, R> Optional<R> getFirstResult(Class<R> clazz, T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        fullWhere.full(t, sql);
        final List<R> resultList = this.getResultListT(clazz, sql).toList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(resultList.get(0));
        }
    }

    /**
     * 分页数据查询
     *
     * @param clazz     查询结果转换实例的实现
     * @param t         分页查询条件
     * @param sql       基础语句
     * @param fullWhere void fullWhere(T find, StringBuilder sqlBuilder, Map&lt;String, Object> parameterMap) 实现
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return org.springframework.data.domain.PageImpl<R>

     * Created DateTime 2022-04-09 11:41
     */
    protected <T, R> PageImpl<R> page2(Class<R> clazz, T t, SelectSql sql, String countSql, WhereFullByParameterList<T> fullWhere) {
        Assert.assertTrue(t instanceof IPaging, "查询条件不包含分页信息。");
        IPaging iPaging = (IPaging) t;
        SelectSql countSql1 = sql.deepClone();
        countSql1.setSqlBase(countSql);
        Long total = this.count(t, countSql1, fullWhere);
        final List<R> gatekeeperCarLogDtoList;
        if (total == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else if (iPaging.getPaging().isAllInOne()) {
            gatekeeperCarLogDtoList = this.list(clazz, t, sql, fullWhere);
        }
        else if (iPaging.getPaging().getPageSize() == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else {
            //记录查询
            gatekeeperCarLogDtoList = pageList(clazz, t, sql, fullWhere);
        }
        return new PageImpl<>(gatekeeperCarLogDtoList, iPaging.getPaging().getPageRequest(), total);
    }

    /**
     * 数据数量查询
     *
     * @param t         分页查询条件
     * @param sql       基础语句
     * @param fullWhere void fullWhere(T find, StringBuilder sqlBuilder, Map&lt;String, Object> parameterMap) 实现
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return org.springframework.data.domain.PageImpl<R>

     * Created DateTime 2022-04-09 11:41
     */
    protected <T, R> Long count(T t, SelectSql sql, String countSql, WhereFullByParameterList<T> fullWhere) {
        SelectSql countSql1 = sql.deepClone();
        countSql1.setSqlBase(countSql);
        return this.count(t, countSql1, fullWhere);
    }

    public class SelectBuilder<T, R> {
        private final Class<R> clazz;
        SelectSql selectSql;
        private String countSql;
        WhereFullByParameterList<T> fullWhere;
        T findWhere;

        public SelectBuilder(Class<R> clazz, T findWhere, SelectSql selectSql, WhereFullByParameterList<T> fullWhere) {
            this.clazz = clazz;
            this.selectSql = selectSql;
            this.fullWhere = fullWhere;
            this.findWhere = findWhere;
        }

        public SelectBuilder<T, R> setCountSql(String countSql) {
            this.countSql = countSql;
            return this;
        }

        public PageImpl<R> buildPage() {
            Assert.assertTrue(StringUtils.hasText(countSql), "countSql语句未设定");
            return page2(clazz, findWhere, selectSql, countSql, fullWhere);
        }

        public List<R> buildList() {
            return list(clazz, findWhere, selectSql, fullWhere);
        }

        public long buildCount() {
            Assert.assertTrue(StringUtils.hasText(countSql), "countSql语句未设定");
            final SelectSql selectSql1 = selectSql.deepClone();
            selectSql1.setSqlBase(countSql);
            return count(findWhere, selectSql1, fullWhere);
        }

        public Optional<R> buildFirstResult() {
            return getFirstResult(clazz, findWhere, selectSql, fullWhere);
        }
    }

    /**
     * 单条记录查询
     *
     * @param dto 查询条件
     * @return java.util.Optional&lt;com.liuhuiyu.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>

     * Created DateTime 2024-01-13 17:36
     */
    public Optional<returnT> findOne(findT dto) {
        WhereFullByParameterList<findT> fullWhere = this::fullWhere;
        Class<returnT> clazz = getReturnTType();
        Optional<returnT> optional = new SelectBuilder<>(clazz, dto, this.getSelectSql(), fullWhere)
                .buildFirstResult();
        optional.ifPresent(IComputedValueFilling::computedValueFilling);
        return optional;
    }

    /**
     * 单条记录查询
     *
     * @param dto 查询条件
     * @return java.util.Optional&lt;com.liuhuiyu.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>

     * Created DateTime 2024-01-13 17:36
     */
    public List<returnT> findList(findT dto) {
        WhereFullByParameterList<findT> fullWhere = this::fullWhere;
        Class<returnT> clazz = getReturnTType();
        List<returnT> list = new SelectBuilder<>(clazz, dto, this.getSelectSql(), fullWhere)
                .buildList();
        for (returnT returnT : list) {
            returnT.computedValueFilling();
        }
        return list;
    }

    /**
     * 单条记录查询
     *
     * @param dto 查询条件
     * @return java.util.Optional&lt;com.liuhuiyu.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>

     * Created DateTime 2024-01-13 17:36
     */
    public PageImpl<returnT> findPage(findT dto) {
        WhereFullByParameterList<findT> fullWhere = this::fullWhere;
        Class<returnT> clazz = getReturnTType();
        PageImpl<returnT> list = new SelectBuilder<>(clazz, dto, this.getSelectSql(), fullWhere)
                .setCountSql(getCountSql())
                .buildPage();
        for (returnT returnT : list) {
            returnT.computedValueFilling();
        }
        return list;
    }

    /**
     * 统计记录数量查询
     *
     * @param dto 查询条件
     * @return java.util.Optional&lt;com.liuhuiyu.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>

     * Created DateTime 2024-01-13 17:36
     */
    public Long findCount(findT dto) {
        WhereFullByParameterList<findT> fullWhere = this::fullWhere;
        Class<returnT> clazz = getReturnTType();
        return new SelectBuilder<>(clazz, dto, this.getSelectSql(), fullWhere)
                .setCountSql(getCountSql())
                .buildCount();
    }

    protected SelectSql getSelectSql() {
        return SelectSqlBuilder.createSelectSql(this.getBaseSql())
                .withSqlWhere(this.getWhereSql())
                .withOrderBy(this.getOrderSql())
                .build();
    }

    protected abstract void fullWhere(findT whereDto, SelectSql selectSql);

    protected abstract String getBaseSql();

    protected abstract String getCountSql();

    protected abstract String getOrderSql();

    protected String getWhereSql() {
        return "where t.DELETE_MARK=0";
    }

    @SuppressWarnings("unchecked")
    public Class<returnT> getReturnTType() {
        return (Class<returnT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
