package com.liuhuiyu.jpa.dm;

import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.AbstractTransactionalView;
import com.liuhuiyu.jpa.dm.util.DmDaoUtil;
import com.liuhuiyu.jpa.sql.WhereFullByParameterList;
import com.liuhuiyu.jpa.util.SqlResolution;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2024-01-11 19:38
 */
public class AbstractTransactionalDmView extends AbstractTransactionalView {
    public static final String SPACE = " ";

    public AbstractTransactionalDmView(EntityManager entityManager) {
        super(entityManager);
    }

    protected <T> Long count(T t, String sql, String baseWhere, WhereFullByParameterList<T> fullWhere) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        List<Object> parameterMap = new ArrayList<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
        return super.selectCount(sqlBuilder.toString(), parameterMap);
    }

    /**
     * 获取列表数据
     *
     * @param clazz     获取数据后的转换
     * @param t         获取条件
     * @param sql       基础查询语句
     * @param baseWhere 基础查询条件（如果无条件此处使用 BLANK_BASE_WHERE）
     * @param order     排序
     * @param fullWhere 填充查询条件
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return java.util.List<R>
     * @author LiuHuiYu
     * Created DateTime 2022-04-25 10:29
     */
    protected <T, R> List<R> list(Class<R> clazz, T t, String sql, String baseWhere, String order, WhereFullByParameterList<T> fullWhere) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        List<Object> parameterMap = new ArrayList<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
        sqlBuilder.append(SPACE).append(order);
        return super.getResultListT(clazz, sqlBuilder.toString(), parameterMap).collect(Collectors.toList());
    }

    /**
     * 获取分页列表数据
     *
     * @param clazz     获取数据后的转换
     * @param t         获取条件
     * @param sql       基础查询语句
     * @param baseWhere 基础查询条件
     * @param order     排序
     * @param fullWhere 填充查询条件
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return java.util.List<R>
     * @author LiuHuiYu
     * Created DateTime 2022-04-25 10:29
     */
    protected <T, R> List<R> pageList(Class<R> clazz, T t, String sql, String baseWhere, String order, WhereFullByParameterList<T> fullWhere) {
        SqlResolution sqlResolution = new SqlResolution(sql);
        Assert.assertTrue(t instanceof IPaging, "查询条件不包含分页信息。");
        IPaging iPaging = (IPaging) t;
        if (!StringUtils.hasText(baseWhere)) {
            baseWhere = " WHERE(1=1)";
        }
        List<Object> parameterMap = new ArrayList<>(0);
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        fullWhere.full(t, sqlBuilder, parameterMap);
        sqlBuilder.append(SPACE).append(order);
        DmDaoUtil.paginationOracleSql(sqlBuilder, iPaging.getPaging());
        return super.getResultListT(clazz, sqlBuilder.toString(), parameterMap,sqlResolution).collect(Collectors.toList());
    }

    protected <T, R> Optional<R> getFirstResult(Class<R> clazz, T t, String sql, String baseWhere, String order, WhereFullByParameterList<T> fullWhere) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        List<Object> parameterMap = new ArrayList<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
        sqlBuilder.append(SPACE).append(order);
        final List<R> resultList = super.getResultListT(clazz, sqlBuilder.toString(), parameterMap).collect(Collectors.toList());
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
     * @param baseWhere 基础查询（带where）
     * @param order     排序(带 order by)
     * @param fullWhere void fullWhere(T find, StringBuilder sqlBuilder, Map<String, Object> parameterMap) 实现
     * @param <R>       返回分页的数据类型
     * @param <T>       分页查询的条件
     * @return org.springframework.data.domain.PageImpl<R>
     * @author LiuHuiYu
     * Created DateTime 2022-04-09 11:41
     */
    protected <T, R> PageImpl<R> page2(Class<R> clazz, T t, String sql, String countSql, String baseWhere, String order, WhereFullByParameterList<T> fullWhere) {
        Assert.assertTrue(t instanceof IPaging, "查询条件不包含分页信息。");
        IPaging iPaging = (IPaging) t;
        Long total = this.count(t, countSql, baseWhere, fullWhere);
        final List<R> gatekeeperCarLogDtoList;
        if (total == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else if (iPaging.getPaging().isAllInOne()) {
            gatekeeperCarLogDtoList = this.list(clazz, t, sql, baseWhere, order, fullWhere);
        }
        else if (iPaging.getPaging().getPageSize() == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else {
            //记录查询
            gatekeeperCarLogDtoList = pageList(clazz, t, sql, baseWhere, order, fullWhere);
        }
        return new PageImpl<>(gatekeeperCarLogDtoList, iPaging.getPaging().getPageRequest(), total);
    }

    public class SelectBuilder<T, R> {
        private final Class<R> clazz;
        private final T findWhere;
        private final String sql;
        private String countSql;
        private final WhereFullByParameterList<T> fullWhere;
        private String order;
        private String baseWhere;

        public SelectBuilder(Class<R> clazz, T findWhere, String sql, WhereFullByParameterList<T> fullWhere) {
            this.clazz = clazz;
            this.findWhere = findWhere;
            this.sql = sql;
            this.fullWhere = fullWhere;
            this.order = "";
            this.baseWhere = " WHERE(1=1) ";
            this.countSql = null;
        }

        public SelectBuilder<T, R> setCountSql(String countSql) {
            this.countSql = countSql;
            return this;
        }

        public SelectBuilder<T, R> setOrder(String order) {
            this.order = order;
            return this;
        }

        public SelectBuilder<T, R> setBaseWhere(String baseWhere) {
            this.baseWhere = baseWhere;
            return this;
        }

        public PageImpl<R> buildPage() {
            Assert.assertTrue(StringUtils.hasText(countSql), "countSql语句未设定");
            return page2(clazz, findWhere, sql, countSql, baseWhere, order, fullWhere);
        }

        public List<R> buildList() {
            return list(clazz, findWhere, sql, baseWhere, order, fullWhere);
        }

        public long buildCount() {
            return count(findWhere, StringUtils.hasText(countSql) ? countSql : sql, baseWhere, fullWhere);
        }

        public Optional<R> buildFirstResult() {
            return getFirstResult(clazz, findWhere, sql, baseWhere, order, fullWhere);
        }
    }
}
