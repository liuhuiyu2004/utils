package com.liuhuiyu.jpa.dm;

import com.liuhuiyu.core.help.sql.SelectSql;
import com.liuhuiyu.core.help.sql.SelectSqlBuilder;
import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.AbstractSqlView;
import com.liuhuiyu.jpa.dm.util.DmDaoUtil;
import com.liuhuiyu.jpa.help.SqlResolution;
import com.liuhuiyu.jpa.sql.WhereFullByParameterList;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/17 9:58
 */
public abstract class AbstractSqlViewDm<returnT extends IComputedValueFilling, findT> extends AbstractSqlView {
    public static final String SPACE = " ";

    public AbstractSqlViewDm(EntityManager entityManager) {
        super(entityManager);
    }

    protected <T> Long count(T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        fullWhere.full(t, sql);
        return super.selectCount(sql);
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
     * @author LiuHuiYu
     * Created DateTime 2022-04-25 10:29
     */
    protected <T, R> List<R> list(Class<R> clazz, T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        fullWhere.full(t, sql);
        return super.getResultListT(clazz, sql).collect(Collectors.toList());
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
     * @author LiuHuiYu
     * Created DateTime 2022-04-25 10:29
     */
    protected <T, R> List<R> pageList(Class<R> clazz, T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        SqlResolution sqlResolution = new SqlResolution(sql.getSql());
        Assert.assertTrue(t instanceof IPaging, "查询条件不包含分页信息。");
        IPaging iPaging = (IPaging) t;
        List<Object> parameterMap = new ArrayList<>(0);
        fullWhere.full(t, sql);
        StringBuilder sqlBuilder = new StringBuilder(sql.getSql());
        DmDaoUtil.paginationOracleSql(sqlBuilder, iPaging.getPaging());
        SelectSql sql1 = new SelectSql(sqlBuilder.toString());
        sql1.setParameterList(sql.getParameterList());
        return super.getResultListT(clazz, sql1, sqlResolution).collect(Collectors.toList());
    }

    protected <T, R> Optional<R> getFirstResult(Class<R> clazz, T t, SelectSql sql, WhereFullByParameterList<T> fullWhere) {
        fullWhere.full(t, sql);
        final List<R> resultList = super.getResultListT(clazz, sql).collect(Collectors.toList());
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
     * @param fullWhere void fullWhere(T find, StringBuilder sqlBuilder, Map<String, Object> parameterMap) 实现
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return org.springframework.data.domain.PageImpl<R>
     * @author LiuHuiYu
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
     * @param fullWhere void fullWhere(T find, StringBuilder sqlBuilder, Map<String, Object> parameterMap) 实现
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return org.springframework.data.domain.PageImpl<R>
     * @author LiuHuiYu
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
     * @return java.util.Optional<cc.rehome.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>
     * @author LiuHuiYu
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
     * @return java.util.Optional<cc.rehome.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>
     * @author LiuHuiYu
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
     * @return java.util.Optional<cc.rehome.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>
     * @author LiuHuiYu
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
     * @return java.util.Optional<cc.rehome.contractor_third_party.domain.AuthorityTree.dto.out.AuthorityTreeDto>
     * @author LiuHuiYu
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
                .withSqlWhere("WHERE(1=1)")
                .withOrderBy(this.getOrderSql())
                .build();
    }

    protected abstract void fullWhere(findT whereDto, SelectSql selectSql);

    protected abstract String getBaseSql();

    protected abstract String getCountSql();

    protected abstract String getOrderSql();

    @SuppressWarnings("unchecked")
    public Class<returnT> getReturnTType() {
        return (Class<returnT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
