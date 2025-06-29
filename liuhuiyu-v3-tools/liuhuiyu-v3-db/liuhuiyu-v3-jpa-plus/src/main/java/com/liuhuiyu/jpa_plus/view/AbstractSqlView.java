package com.liuhuiyu.jpa_plus.view;

import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa_plus.conditions.SqlConditionWrapper;
import com.liuhuiyu.jpa_plus.conditions.SqlGroupByWrapper;
import com.liuhuiyu.jpa_plus.conditions.SqlOrderByWrapper;
import com.liuhuiyu.jpa_plus.conditions.SqlSelectWrapper;
import com.liuhuiyu.jpa_plus.sql.SqlResolution;
import com.liuhuiyu.jpa_plus.util.DataBaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.intellij.lang.annotations.Language;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 功能<p>
 * Created on 2025/6/22 11:43
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
    protected EntityManager entityManager;

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
        SqlSelectWrapper selectSql = SqlSelectWrapper.Builder.create(getBaseSql())
                .setWhere(sqlWhere())
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
        SqlSelectWrapper selectSql = SqlSelectWrapper.Builder.create(getBaseSql())
                .setWhere(sqlWhere())
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
        SqlSelectWrapper selectSql = SqlSelectWrapper.Builder.create(getBaseSql())
                .setWhere(sqlWhere())
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
    protected abstract void fullWhere(findT whereDto, SqlSelectWrapper selectSql);

    //region 基本sql信息获取

    protected abstract String getBaseSql();

    protected abstract String getCountSql();

    protected abstract SqlConditionWrapper sqlWhere();

    protected SqlOrderByWrapper orderBy() {
        return new SqlOrderByWrapper();
    }

    protected SqlGroupByWrapper groupBy() {
        return new SqlGroupByWrapper();
    }

    protected SqlConditionWrapper having() {
        return new SqlConditionWrapper();
    }

    //endregion
    @SuppressWarnings("unchecked")
    public Class<returnT> getReturnTType() {
        return (Class<returnT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    public class SelectBuilder {
        private final Class<returnT> clazz;
        SqlSelectWrapper selectSql;
        private String countSql;
        WhereFullByParameterList<findT> fullWhere;
        findT findWhere;

        public SelectBuilder(Class<returnT> clazz, findT findWhere, SqlSelectWrapper selectSql, WhereFullByParameterList<findT> fullWhere) {
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

//        public long buildCount() {
//            Assert.assertTrue(StringUtils.hasText(countSql), "countSql语句未设定");
//            final SqlSelectWrapper selectSql = this.selectSql.deepClone();
//            selectSql.setSqlBase(countSql);

        /// /            this.fullWhere.full(this.findWhere, selectSql);
//            return getCount(this.findWhere, selectSql, this.fullWhere);
//        }
        public Optional<returnT> buildFirstResult() {
            return getFirstResult(clazz, findWhere, selectSql, fullWhere);
        }
    }

    private long getCount(findT t, SqlSelectWrapper selectSql, WhereFullByParameterList<findT> fullWhere) {
        fullWhere.full(t, selectSql);
        return this.selectCount(selectSql);
    }

    @SuppressWarnings("all")
    protected Long selectCount(SqlSelectWrapper selectSql) {
        //对于聚合sql语句，需要用子查询解决

        @Language("SQL")
        String sqlCommand = selectSql.getSql();
        //对于聚合sql语句，需要用子查询解决
        if (selectSql.getGroupBy().getConditional().isPresent()) {
            sqlCommand = "select count(1) from (" + sqlCommand + ")";
        }
        final Query nativeQuery = entityManager.createNativeQuery(sqlCommand);
        for (int i = 0, length = selectSql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, selectSql.getParameterList().get(i));
        }
        final Object singleResult = nativeQuery.getSingleResult();
        return Long.parseLong(singleResult.toString());
    }

    @SuppressWarnings("all")
    private List<returnT> getList(Class<returnT> clazz, findT findWhere, SqlSelectWrapper selectSql, WhereFullByParameterList<findT> fullWhere) {
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

    private PageImpl<returnT> getPage(Class<returnT> clazz, findT findWhere, SqlSelectWrapper selectSql, String countSql, WhereFullByParameterList<findT> fullWhere) {
        Assert.assertTrue(findWhere instanceof IPaging, "查询条件不包含分页信息。");
        IPaging iPaging = (IPaging) findWhere;
        SqlSelectWrapper countSql1 = new SqlSelectWrapper(countSql, selectSql.getWhere().deepClone(), selectSql.getGroupBy().deepClone(), selectSql.getHaving().deepClone(), selectSql.getOrderBy().deepClone());
        long total = this.getCount(findWhere, countSql1, fullWhere);
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
     * @param findWhere 获取条件
     * @param selectSql 基础查询语句
     * @param fullWhere 填充查询条件
     * @return java.util.List<R>
     * Created DateTime 2022-04-25 10:29
     */
    @SuppressWarnings("all")
    protected List<returnT> pageList(Class<returnT> clazz, findT findWhere, SqlSelectWrapper selectSql, WhereFullByParameterList<findT> fullWhere) {
        //填充sql
        fullWhere.full(findWhere, selectSql);
        IPaging iPaging = (IPaging) findWhere;
        String sqlCommand = getPageSql(iPaging, selectSql.getSql());
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

    /**
     * 功能描述<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/6/22 13:15
     *
     * @param iPaging 分页
     * @param sql     sql语句
     * @return java.lang.String
     */
    protected String getPageSql(IPaging iPaging, String sql) {
        return getMySqlPageSql(iPaging, sql);
    }

    protected String getMySqlPageSql(IPaging iPaging, String sql) {
        return sql + " limit " + iPaging.getPaging().beginRowNo() + ", " + iPaging.getPaging().getPageSize();
    }
    @SuppressWarnings("unused")
    protected String getOraclePageSql(IPaging paging, String sql) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        String rowNumName;
        for (int i = 0; ; i++) {
            rowNumName = "rowno_" + i;
            if (!sql.toLowerCase(Locale.ROOT).contains(rowNumName)) {
                break;
            }
        }
        sqlBuilder.insert(0, "SELECT * FROM (SELECT t_pagination.*, ROWNUM AS " + rowNumName + " FROM(");
        sqlBuilder.append(") t_pagination WHERE ROWNUM <= ")
                .append(paging.getPaging().endRowNo())
                .append(") table_alias WHERE table_alias.")
                .append(rowNumName)
                .append(" >= ")
                .append(paging.getPaging().beginRowNo());
        return sqlBuilder.toString();
    }

    //    @SuppressWarnings("SQL")
    @SuppressWarnings("all")
    private Optional<returnT> getFirstResult(Class<returnT> clazz, findT findWhere, SqlSelectWrapper selectSql, WhereFullByParameterList<findT> fullWhere) {
        //填充sql
        fullWhere.full(findWhere, selectSql);
        @Language("SQL")
        String sqlCommand = selectSql.getSql();
        final Query nativeQuery = entityManager.createNativeQuery(sqlCommand);

        SqlResolution sqlResolution = new SqlResolution(sqlCommand);
        for (int i = 0, length = selectSql.getParameterList().size(); i < length; i++) {
            nativeQuery.setParameter(i + 1, selectSql.getParameterList().get(i));
        }
        final Optional<?> first = nativeQuery.getResultStream().findFirst();
        return first.map(v -> DataBaseUtil.objToT(v, clazz, sqlResolution));
    }
}
