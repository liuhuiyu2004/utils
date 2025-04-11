package com.liuhuiyu.jpa.view;

import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.sql.*;
import com.liuhuiyu.jpa.util.DataBaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 功能<p>
 * Created on 2025/4/10 22:14
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
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
     * 单条记录获取<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/4/10 22:58
     *
     * @param dto 查询条件
     * @return java.util.Optional<returnT>
     */
    public Optional<returnT> findOne(findT dto) {
        SelectSql selectSql = SelectSql.Builder.create(getBaseSql())
                .setSqlWhere(sqlWhere())
                .setGroupBy(groupBy())
                .setHaving(having())
                .setOrderBy(orderBy())
                .build();
        Optional<returnT> optional = new SelectBuilder(getReturnTType(), dto, selectSql, this::fullWhere)
                .buildFirstResult();
        optional.ifPresent(IComputedValueFilling::computedValueFilling);
        return optional;
    }

    /**
     * 列表获取<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/4/10 23:01
     *
     * @param dto 查询条件
     * @return java.util.List<returnT>
     */
    public List<returnT> findList(findT dto) {
        SelectSql selectSql = SelectSql.Builder.create(getBaseSql())
                .setSqlWhere(sqlWhere())
                .setGroupBy(groupBy())
                .setHaving(having())
                .setOrderBy(orderBy())
                .build();
        List<returnT> list = new SelectBuilder(getReturnTType(), dto, selectSql, this::fullWhere)
                .buildList();
        for (returnT returnT : list) {
            returnT.computedValueFilling();
        }
        return list;
    }

    public PageImpl<returnT> findPage(findT dto) {
        SelectSql selectSql = SelectSql.Builder.create(getBaseSql())
                .setSqlWhere(sqlWhere())
                .setGroupBy(groupBy())
                .setHaving(having())
                .setOrderBy(orderBy())
                .build();
        PageImpl<returnT> page = new SelectBuilder(getReturnTType(), dto, selectSql, this::fullWhere)
                .setCountSql(getCountSql())
                .buildPage();
        for (returnT returnT : page) {
            returnT.computedValueFilling();
        }
        return page;
    }

    //分页获取
    protected abstract void fullWhere(findT whereDto, SelectSql selectSql);

    //region 基本sql信息获取

    protected abstract String getBaseSql();

    protected abstract String getCountSql();

    protected abstract ConditionalFiltering sqlWhere();

    protected SqlOrderBy orderBy() {
        return new SqlOrderBy();
    }

    protected SqlGroupBy groupBy() {
        return new SqlGroupBy();
    }

    protected ConditionalFiltering having() {
        return new ConditionalFiltering();
    }

    //endregion
    @SuppressWarnings("unchecked")
    public Class<returnT> getReturnTType() {
        return (Class<returnT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    public class SelectBuilder {
        private final Class<returnT> clazz;
        SelectSql selectSql;
        private String countSql;
        WhereFullByParameterList<findT> fullWhere;
        findT findWhere;

        public SelectBuilder(Class<returnT> clazz, findT findWhere, SelectSql selectSql, WhereFullByParameterList<findT> fullWhere) {
            this.clazz = clazz;
            this.selectSql = selectSql;
            this.fullWhere = fullWhere;
            this.findWhere = findWhere;
        }

        public SelectBuilder setCountSql(String countSql) {
            this.countSql = countSql;
            return this;
        }

        public PageImpl<returnT> buildPage() {
            Assert.assertTrue(StringUtils.hasText(countSql), "countSql语句未设定");
            return getPage(clazz, findWhere, selectSql, countSql, fullWhere);
        }

        public List<returnT> buildList() {
            return getList(clazz, findWhere, selectSql, fullWhere);
        }

        public long buildCount() {
            Assert.assertTrue(StringUtils.hasText(countSql), "countSql语句未设定");
            final SelectSql selectSql = this.selectSql.deepClone();
            selectSql.setSqlBase(countSql);
//            this.fullWhere.full(this.findWhere, selectSql);
            return getCount(this.findWhere, selectSql, this.fullWhere);
        }

        public Optional<returnT> buildFirstResult() {
            return getFirstResult(clazz, findWhere, selectSql, fullWhere);
        }
    }

    private long getCount(findT t, SelectSql selectSql, WhereFullByParameterList<findT> fullWhere) {
        fullWhere.full(t, selectSql);
        return this.selectCount(selectSql);
    }

    protected Long selectCount(SelectSql selectSql) {
        //对于聚合sql语句，需要用子查询解决
        String sqlCommand = selectSql.getSql();
        //对于聚合sql语句，需要用子查询解决
        if (StringUtils.hasText(selectSql.getGroupBy().getGroupBy())) {
            sqlCommand = "select count(1) from (" + sqlCommand + ")";
        }
        final Query nativeQuery = entityManager.createNativeQuery(sqlCommand);
        for (int i = 0, length = selectSql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, selectSql.getParameterList().get(i));
        }
        final Object singleResult = nativeQuery.getSingleResult();
        return Long.parseLong(singleResult.toString());
    }

    private List<returnT> getList(Class<returnT> clazz, findT findWhere, SelectSql selectSql, WhereFullByParameterList<findT> fullWhere) {
        //填充sql
        fullWhere.full(findWhere, selectSql);
        String sqlCommand = selectSql.getSql();
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

    private PageImpl<returnT> getPage(Class<returnT> clazz, findT findWhere, SelectSql selectSql, String countSql, WhereFullByParameterList<findT> fullWhere) {
        Assert.assertTrue(findWhere instanceof IPaging, "查询条件不包含分页信息。");
        IPaging iPaging = (IPaging) findWhere;
        SelectSql countSql1 = selectSql.deepClone();
        countSql1.setSqlBase(countSql);
        Long total = this.getCount(findWhere, countSql1, fullWhere);
        final List<returnT> gatekeeperCarLogDtoList;
        if (total == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else if (iPaging.getPaging().isAllInOne()) {
            gatekeeperCarLogDtoList = this.getList(clazz, findWhere, selectSql, fullWhere);
        }
        else if (iPaging.getPaging().getPageSize() == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else {
            //记录查询
            gatekeeperCarLogDtoList = pageList(clazz, findWhere, selectSql, fullWhere);
        }
        return new PageImpl<>(gatekeeperCarLogDtoList, iPaging.getPaging().getPageRequest(), total);
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
    private Optional<returnT> getFirstResult(Class<returnT> clazz, findT findWhere, SelectSql selectSql, WhereFullByParameterList<findT> fullWhere) {
        //填充sql
        fullWhere.full(findWhere, selectSql);
        String sqlCommand = selectSql.getSql();
        SqlResolution sqlResolution = new SqlResolution(sqlCommand);
        final Query nativeQuery = entityManager.createNativeQuery(sqlCommand);
        for (int i = 0, length = selectSql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, selectSql.getParameterList().get(i));
        }
        final Optional<?> first = nativeQuery.getResultStream().findFirst();
        return first.map(v -> DataBaseUtil.objToT(v, clazz, sqlResolution));
    }
}
