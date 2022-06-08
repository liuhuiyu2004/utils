package com.liuhuiyu.jpa.oracle.dao;

import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.BaseView;
import com.liuhuiyu.jpa.DaoOperator;
import com.liuhuiyu.jpa.WhereFull;
import com.liuhuiyu.jpa.oracle.util.OracleDaoUtil;
import org.springframework.data.domain.PageImpl;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 8:15
 */
public abstract class OracleBaseView extends BaseView {
    public OracleBaseView(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 数量查询
     *
     * @param t         条件
     * @param sql       原始语句
     * @param fullWhere 填充条件
     * @param <T>       分页查询的条件
     * @return java.lang.Long
     * @author LiuHuiYu
     * Created DateTime 2022-02-21 16:24
     */
    protected <T> Long count(T t, String sql, String baseWhere, WhereFull<T> fullWhere) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        Map<String, Object> parameterMap = new HashMap<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
        OracleDaoUtil.countOracleSql(sqlBuilder);
        return super.selectCount(sqlBuilder.toString(), parameterMap);
    }

    /**
     * 获取分页列表数据
     *
     * @param b         获取数据后的转换
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
    protected <T extends IPaging, R> List<R> pageList(DaoOperator<R> b, T t, String sql, String baseWhere, String order, WhereFull<T> fullWhere) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        Map<String, Object> parameterMap = new HashMap<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
        sqlBuilder.append(SPACE).append(order);
        OracleDaoUtil.paginationOracleSql(sqlBuilder, t.getPaging());
        return super.getResultList(b, sqlBuilder.toString(), parameterMap);
    }

    /**
     * 分页查询建造者
     *
     * @author LiuHuiYu
     * Created DateTime 2022-05-02 8:33
     */
    public class PageImplBuilder<T extends IPaging, R> {
        private final DaoOperator<R> b;
        private final T t;
        private final String sql;
        private final WhereFull<T> fullWhere;
        private String order;
        private String baseWhere;

        public PageImplBuilder(DaoOperator<R> b, T t, String sql, WhereFull<T> fullWhere) {
            this.b = b;
            this.t = t;
            this.sql = sql;
            this.fullWhere = fullWhere;
            this.order = "";
            this.baseWhere = " WHERE(1=1) ";
        }

        public PageImplBuilder<T, R> order(String order) {
            this.order = order;
            return this;
        }

        public PageImplBuilder<T, R> baseWhere(String baseWhere) {
            this.baseWhere = baseWhere;
            return this;
        }

        public PageImpl<R> buildPage() {
            return page(b, t, sql, baseWhere, order, fullWhere);
        }
    }

    public static final String SPACE = " ";

    /**
     * 获取列表数据
     *
     * @param b         获取数据后的转换
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
    protected <T extends IPaging, R> List<R> list(DaoOperator<R> b, T t, String sql, String baseWhere, String order, WhereFull<T> fullWhere) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        Map<String, Object> parameterMap = new HashMap<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
        sqlBuilder.append(SPACE).append(order);
        return super.getResultList(b, sqlBuilder.toString(), parameterMap);
    }

    /**
     * 分页数据查询
     *
     * @param b         查询结果转换实例的实现
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
    protected <T extends IPaging, R> PageImpl<R> page(DaoOperator<R> b, T t, String sql, String baseWhere, String order, WhereFull<T> fullWhere) {
        Long total = this.count(t, sql, baseWhere, fullWhere);
        final List<R> gatekeeperCarLogDtoList;
        if (total == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else if (t.getPaging().isAllInOne()) {
            gatekeeperCarLogDtoList = this.list(b, t, sql, baseWhere, order, fullWhere);
        }
        else if (t.getPaging().getPageSize() == 0) {
            gatekeeperCarLogDtoList = Collections.emptyList();
        }
        else {
            //记录查询
            gatekeeperCarLogDtoList = pageList(b, t, sql, baseWhere, order, fullWhere);
        }
        return new PageImpl<>(gatekeeperCarLogDtoList, t.getPaging().getPageRequest(), total);
    }

    /**
     * 模糊匹配 like值
     *
     * @param value 值
     * @return java.lang.String
     * @author LiuHuiYu
     * Created DateTime 2022-06-02 18:44
     */
    protected static String likeValue(String value) {
        return likeValue(value, true, true, true);
    }

    /**
     * like值
     *
     * @param value 值
     * @param trim  去掉首尾空格
     * @param head  头部模糊匹配
     * @param tail  尾部模糊匹配
     * @return java.lang.String
     * @author LiuHuiYu
     * Created DateTime 2022-06-02 18:43
     */
    protected static String likeValue(String value, Boolean trim, Boolean head, Boolean tail) {
        return (head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : "");
    }
}
