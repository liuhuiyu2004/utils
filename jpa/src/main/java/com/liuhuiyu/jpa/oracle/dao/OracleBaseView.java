package com.liuhuiyu.jpa.oracle.dao;

import com.google.common.base.Joiner;
import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.BaseView;
import com.liuhuiyu.jpa.DaoOperator;
import com.liuhuiyu.jpa.WhereFull;
import com.liuhuiyu.jpa.oracle.util.OracleDaoUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

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

    public static final String BLANK_BASE_WHERE = " ";

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
        if (!StringUtils.hasText(baseWhere) && !BLANK_BASE_WHERE.equals(baseWhere)) {
            baseWhere = "WHERE(1=1)";
        }
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        sqlBuilder.append(SPACE).append(order);
        Map<String, Object> parameterMap = new HashMap<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
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

        public List<R> buildList() {
            return list(b, t, sql, baseWhere, order, fullWhere);
        }

        public long buildCount() {
            return count(t, sql, baseWhere, fullWhere);
        }
    }

    public static final String SPACE = " ";

    /**
     * 获取列表数据
     *
     * @param b         获取数据后的转换
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
     * 建议构建继承SqlCommandPackage的类，使用SqlCommandPackage实现。
     *
     * @param value 值
     * @return java.lang.String
     * @author LiuHuiYu
     * Created DateTime 2022-06-02 18:44
     */
    @Deprecated
    protected static String likeValue(String value) {
        return likeValue(value, true, true, true);
    }

    /**
     * like值
     * 建议构建继承SqlCommandPackage的类，使用SqlCommandPackage实现。
     *
     * @param value 值
     * @param trim  去掉首尾空格
     * @param head  头部模糊匹配
     * @param tail  尾部模糊匹配
     * @return java.lang.String
     * @author LiuHuiYu
     * Created DateTime 2022-06-02 18:43
     */
    @Deprecated
    protected static String likeValue(String value, Boolean trim, Boolean head, Boolean tail) {
        return (head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : "");
    }

    /**
     * SQL原生封装
     *
     * @author LiuHuiYu
     * Created DateTime 2022-11-20 8:26
     */
    protected static abstract class SqlCommandPackage<T> {
        protected T findDto;
        protected StringBuilder sqlBuilder;
        protected Map<String, Object> parameterMap;
        protected Joiner joiner = Joiner.on(",").skipNulls();

        /**
         * SQL原生封装 构建函数
         *
         * @param findDto      查询条件
         * @param sqlBuilder   sqlBuilder
         * @param parameterMap 参数
         * @author LiuHuiYu
         * Created DateTime 2022-11-20 8:27
         */
        public SqlCommandPackage(T findDto, StringBuilder sqlBuilder, Map<String, Object> parameterMap) {
            this.findDto = findDto;
            this.sqlBuilder = sqlBuilder;
            this.parameterMap = parameterMap;
        }

        /**
         * 封装 in 条件
         *
         * @param parameterName 参数名称头
         * @param fieldName     字段名称
         * @param data          查询的数据
         * @param notIn         使用 not in
         * @param isNull        包含空 OR(fieldName is null)
         * @author LiuHuiYu
         * Created DateTime 2022-11-20 8:28
         */
        protected void inPackage(String parameterName, String fieldName, T[] data, Boolean notIn, Boolean isNull) {
            if (data != null && data.length > 0) {
                String[] names = new String[data.length];
                sqlBuilder.append("AND((").append(fieldName).append(notIn ? " NOT" : "").append(" IN(");
                for (int i = 0; i < data.length; i++) {
                    names[i] = ":" + parameterName + i;
                    parameterMap.put(parameterName + i, data[i]);
                }
                sqlBuilder.append(joiner.join(names)).append("))");
                if (isNull) {
                    sqlBuilder.append("OR(").append(fieldName).append(" is null)");
                }
                sqlBuilder.append(")");
            }
        }


        /**
         * 模糊匹配 like值
         *
         * @param value 值
         * @author LiuHuiYu
         * Created DateTime 2022-06-02 18:44
         */
        protected void likeValue(String parameterName, String fieldName, String value) {
            likeValue(parameterName, fieldName, value, true, true, true);
        }

        /**
         * like值
         *
         * @param value 值
         * @param trim  去掉首尾空格
         * @param head  头部模糊匹配
         * @param tail  尾部模糊匹配
         * @author LiuHuiYu
         * Created DateTime 2022-06-02 18:43
         */
        protected void likeValue(String parameterName, String fieldName, String value, Boolean trim, Boolean head, Boolean tail) {
            final String s = (head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : "");
            this.sqlBuilder.append("AND(").append(parameterName).append(" LIKE :").append(fieldName).append(")");
            this.parameterMap.put(fieldName, s);
        }

        /**
         * 命令封装
         *
         * @author LiuHuiYu
         * Created DateTime 2022-11-19 21:32
         */
        public abstract void commandPackage();
    }
}
