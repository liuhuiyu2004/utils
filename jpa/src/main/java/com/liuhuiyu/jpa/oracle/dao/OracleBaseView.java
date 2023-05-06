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
import java.util.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 8:15
 */
public abstract class OracleBaseView extends BaseView {
    public OracleBaseView(DataSource dataSource) {
        super(dataSource);
    }

    //region 查询

    public static final String BLANK_BASE_WHERE = " ";

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
        return count(t, sql, baseWhere, fullWhere, false);
    }

    protected <T> Long count(T t, String sql, String baseWhere, WhereFull<T> fullWhere, boolean sqlPrototype) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        if (!sqlPrototype) {
            OracleDaoUtil.countOracleSql(sqlBuilder);
        }
        sqlBuilder.append(SPACE).append(baseWhere);
        Map<String, Object> parameterMap = new HashMap<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
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
        if (!StringUtils.hasText(baseWhere) && !BLANK_BASE_WHERE.equals(baseWhere)) {
            baseWhere = "WHERE(1=1)";
        }
        Map<String, Object> parameterMap = new HashMap<>(0);
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        fullWhere.full(t, sqlBuilder, parameterMap);
        sqlBuilder.append(SPACE).append(order);
        OracleDaoUtil.paginationOracleSql(sqlBuilder, t.getPaging());
        return super.getResultList(b, sqlBuilder.toString(), parameterMap);
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
        return page(b, t, sql, null, baseWhere, order, fullWhere);
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
    protected <T extends IPaging, R> PageImpl<R> page(DaoOperator<R> b, T t, String sql, String countSql, String baseWhere, String order, WhereFull<T> fullWhere) {
        Long total = this.count(t, StringUtils.hasText(countSql) ? countSql : sql, baseWhere, fullWhere, StringUtils.hasText(countSql));
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

    protected <T extends IPaging, R> Optional<R> getFirstResult(DaoOperator<R> b, T t, String sql, String baseWhere, String order, WhereFull<T> fullWhere) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(SPACE).append(baseWhere);
        Map<String, Object> parameterMap = new HashMap<>(0);
        fullWhere.full(t, sqlBuilder, parameterMap);
        sqlBuilder.append(SPACE).append(order);
        final List<R> resultList = super.getResultList(b, sqlBuilder.toString(), parameterMap);
        if (resultList.size() == 0) {
            return Optional.empty();
        }
        else {
            return Optional.of(resultList.get(0));
        }
    }
    //endregion

    //region 辅助功能 已经准备作废

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
    //endregion

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
        private String countSql;
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
            this.countSql = null;
        }

        /**
         * 设置独立的count查询语句
         *
         * @param countSql count查询语句
         * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.PageImplBuilder<T, R>
         * @author LiuHuiYu
         * Created DateTime 2022-11-20 9:36
         */
        public PageImplBuilder<T, R> setCountSql(String countSql) {
            this.countSql = countSql;
            return this;
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
            return page(b, t, sql, countSql, baseWhere, order, fullWhere);
        }

        public List<R> buildList() {
            return list(b, t, sql, baseWhere, order, fullWhere);
        }

        public long buildCount() {
            return count(t, StringUtils.hasText(countSql) ? countSql : sql, baseWhere, fullWhere, StringUtils.hasText(countSql));
        }

        public Optional<R> buildFirstResult() {
            return getFirstResult(b, t, sql, baseWhere, order, fullWhere);
        }
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
         * @author LiuHuiYu
         * Created DateTime 2022-11-20 8:28
         * @deprecated 使用 condition 方法进行操作
         */
        @Deprecated
        protected <P> void inPackage(String parameterName, String fieldName, P[] data) {
            inPackage(parameterName, fieldName, data, false, false);
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
         * @deprecated 使用 condition 方法进行操作
         */
        @Deprecated
        protected <P> void inPackage(String parameterName, String fieldName, P[] data, Boolean notIn, Boolean isNull) {
            if (data != null && data.length > 0) {
                String[] names = new String[data.length];
                this.sqlBuilder.append("AND((").append(fieldName).append(notIn ? " NOT" : "").append(" IN(");
                for (int i = 0; i < data.length; i++) {
                    names[i] = ":" + parameterName + i;
                    this.parameterMap.put(parameterName + i, data[i]);
                }
                this.sqlBuilder.append(joiner.join(names)).append("))");
                if (isNull) {
                    sqlBuilder.append("OR(").append(fieldName).append(" is null)");
                }
                this.sqlBuilder.append(")");
            }
        }


        /**
         * 封装 数据段互相包含（开区间 位置相同）条件
         *
         * @param minParameterName 最小值参数名
         * @param maxParameterName 最大值参数名
         * @param minFieldName     最小值字段
         * @param maxFieldName     最大值字段
         * @param minValue         最小值
         * @param maxValue         最大值
         * @author LiuHuiYu
         * Created DateTime 2022-12-01 10:23
         * @deprecated 使用 condition 方法进行操作
         */
        @Deprecated
        protected <P> void inclusion(String minParameterName, String maxParameterName, String minFieldName, String maxFieldName, P minValue, P maxValue) {
            this.sqlBuilder.append("and (");
            this.sqlBuilder.append("((").append(minFieldName).append(" < :").append(minParameterName).append(") and (")
                    .append(maxFieldName).append(" > :").append(minParameterName).append("))");
            this.sqlBuilder.append("or");
            this.sqlBuilder.append("((").append(minFieldName).append(" < :").append(maxParameterName).append(") and (")
                    .append(maxFieldName).append(" > :").append(maxParameterName).append("))");
            this.sqlBuilder.append("or");
            this.sqlBuilder.append("((").append(minFieldName).append(" >= :").append(minParameterName).append(") and (")
                    .append(maxFieldName).append(" <= :").append(maxParameterName).append("))");
            this.sqlBuilder.append(")");
            this.parameterMap.put(minParameterName, minValue);
            this.parameterMap.put(maxParameterName, maxValue);
        }

        /**
         * 模糊匹配 like值
         *
         * @param value 值
         * @author LiuHuiYu
         * Created DateTime 2022-06-02 18:44
         * @deprecated 使用 condition 方法进行操作
         */
        @Deprecated
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
         * @deprecated 使用 condition 方法进行操作
         */
        @Deprecated
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
        protected abstract void commandPackage();

        /**
         * 执行命令封装
         * @author LiuHuiYu
         * Created DateTime 2023-05-06 11:42
         */
        public void runCommandPackage() {
            this.commandPackage();
            if (this.conditionalDepth != 0) {
                throw new RuntimeException("条件层级未闭锁，请检查是否所有的 begin 都有 对应的 end。");
            }
        }

        /**
         * 功能描述
         *
         * @param minFieldName 最大值字段
         * @param maxFieldName 最小值字段
         * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
         * @author LiuHuiYu
         * Created DateTime 2023-03-29 9:25
         */
        public Condition<T> conditionAnd(String minFieldName, String maxFieldName) {
            Condition<T> f = new Condition<>(this);
            f.minFieldName = minFieldName;
            f.maxFieldName = maxFieldName;
            f.condition = " AND ";
            return f;
        }

        /**
         * 条件生成
         *
         * @param fieldName 字段名称
         * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
         * @author LiuHuiYu
         * Created DateTime 2023-02-23 23:56
         */
        public Condition<T> conditionAnd(String fieldName) {
            Condition<T> f = new Condition<>(this);
            f.fieldName = fieldName;
            f.condition = " AND ";
            return f;
        }

        public Condition<T> conditionOr(String minFieldName, String maxFieldName) {
            Condition<T> f = new Condition<>(this);
            f.minFieldName = minFieldName;
            f.maxFieldName = maxFieldName;
            f.condition = " OR ";
            return f;
        }

        /**
         * 条件生成
         *
         * @param fieldName 字段名称
         * @return com.liuhuiyu.jpa.oracle.dao.OracleBaseView.SqlCommandPackage.Condition<T>
         * @author LiuHuiYu
         * Created DateTime 2023-02-23 23:56
         */
        public Condition<T> conditionOr(String fieldName) {
            Condition<T> f = new Condition<>(this);
            f.fieldName = fieldName;
            f.condition = " OR ";
            return f;
        }

        @Deprecated
        public Condition<T> conditionNone(String fieldName) {
            Condition<T> f = new Condition<>(this);
            f.fieldName = fieldName;
            f.condition = "";
            return f;
        }

        int conditionalDepth = 0;

        public SqlCommandPackage<T> conditionalBeginAnd() {
            this.conditionalDepth++;
            this.sqlBuilder.append("AND(");
            return this;
        }

        public Condition<T> conditionalBeginAnd(String fieldName) {
            this.conditionalDepth++;
            this.sqlBuilder.append("AND(");
            Condition<T> f = new Condition<>(this);
            f.fieldName = fieldName;
            f.condition = "";
            return f;
        }

        public Condition<T> conditionalBeginAnd(String minFieldName, String maxFieldName) {
            this.conditionalDepth++;
            this.sqlBuilder.append("AND(");
            Condition<T> f = new Condition<>(this);
            f.minFieldName = minFieldName;
            f.maxFieldName = maxFieldName;
            f.condition = "";
            return f;
        }

        public SqlCommandPackage<T> conditionalBeginOr() {
            this.conditionalDepth++;
            this.sqlBuilder.append("OR(");
            return this;
        }

        public Condition<T> conditionalBeginOr(String fieldName) {
            this.conditionalDepth++;
            this.sqlBuilder.append("OR(");
            Condition<T> f = new Condition<>(this);
            f.fieldName = fieldName;
            f.condition = "";
            return f;
        }

        public Condition<T> conditionalBeginOr(String minFieldName, String maxFieldName) {
            this.conditionalDepth++;
            this.sqlBuilder.append("OR(");
            Condition<T> f = new Condition<>(this);
            f.minFieldName = minFieldName;
            f.maxFieldName = maxFieldName;
            f.condition = "";
            return f;
        }

        public SqlCommandPackage<T> conditionalEnd() {
            if (this.conditionalDepth <= 0) {
                throw new RuntimeException("条件层级不能为负");
            }
            this.conditionalDepth--;
            this.sqlBuilder.append(")");
            return this;
        }

        protected static class Condition<T> {
            final SqlCommandPackage<T> sqlCommandPackage;
            String fieldName;
            String minFieldName;
            String maxFieldName;
            String condition;

            private void checkField() {
                if (!StringUtils.hasText(fieldName)) {
                    throw new RuntimeException("未设定字段名称");
                }
            }

            private void checkField2() {
                if (!StringUtils.hasText(minFieldName) || !StringUtils.hasText(maxFieldName)) {
                    throw new RuntimeException("双字段名称设定不完整。");
                }
            }

            private Condition(SqlCommandPackage<T> sqlCommandPackage) {
                this.sqlCommandPackage = sqlCommandPackage;
            }

            /**
             * like值
             *
             * @param value         值
             * @param parameterName 参数名称
             * @author LiuHuiYu
             * Created DateTime 2022-06-02 18:43
             */
            public SqlCommandPackage<T> likeValue(String parameterName, String value) {
                return likeValue(parameterName, value, true, true, true);
            }

            /**
             * like值
             *
             * @param value         值
             * @param parameterName 参数名称
             * @param trim          去掉首尾空格
             * @param head          头部模糊匹配
             * @param tail          尾部模糊匹配
             * @author LiuHuiYu
             * Created DateTime 2022-06-02 18:43
             */
            public SqlCommandPackage<T> likeValue(String parameterName, String value, Boolean trim, Boolean head, Boolean tail) {
                this.checkField();
                if (value == null) {
                    return this.sqlCommandPackage;
                }
                final String s = (head ? "%" : "") + (trim ? value.trim() : value) + (tail ? "%" : "");
                this.sqlCommandPackage.sqlBuilder.append(condition).append("(").append(this.fieldName).append(" LIKE :").append(parameterName).append(")");
                this.sqlCommandPackage.parameterMap.put(parameterName, s);
                return this.sqlCommandPackage;
            }

            /**
             * 封装 in 条件
             *
             * @param parameterName 参数名称头
             * @param data          查询的数据
             * @author LiuHuiYu
             * Created DateTime 2022-11-20 8:28
             */
            public <P> SqlCommandPackage<T> inPackage(String parameterName, P[] data) {
                return inPackage(parameterName, data, false, false);
            }

            /**
             * 封装 in 条件
             *
             * @param parameterName 参数名称头
             * @param data          查询的数据
             * @param notIn         使用 not in
             * @param isNull        包含空 OR(fieldName is null)
             * @author LiuHuiYu
             * Created DateTime 2022-11-20 8:28
             */
            public <P> SqlCommandPackage<T> inPackage(String parameterName, P[] data, Boolean notIn, Boolean isNull) {
                this.checkField();
                if (data != null && data.length > 0) {
                    Joiner joiner = Joiner.on(",").skipNulls();
                    String[] names = new String[data.length];
                    this.sqlCommandPackage.sqlBuilder.append(condition).append("((").append(fieldName).append(notIn ? " NOT" : "").append(" IN(");
                    for (int i = 0; i < data.length; i++) {
                        names[i] = ":" + parameterName + i;
                        this.sqlCommandPackage.parameterMap.put(parameterName + i, data[i]);
                    }
                    this.sqlCommandPackage.sqlBuilder.append(joiner.join(names)).append("))");
                    if (isNull) {
                        this.sqlCommandPackage.sqlBuilder.append("OR(").append(fieldName).append(" is null)");
                    }
                    this.sqlCommandPackage.sqlBuilder.append(")");
                }
                return this.sqlCommandPackage;
            }

            public <P> SqlCommandPackage<T> between(String beginParameterName, P beginValue, String endParameterName, P endValue) {
                this.checkField();
                if (beginValue == null || endValue == null) {
                    return this.sqlCommandPackage;
                }
                this.sqlCommandPackage.sqlBuilder.append(condition)
                        .append("(").append(this.fieldName)
                        .append(" between :").append(beginParameterName)
                        .append(" and :")
                        .append(endParameterName)
                        .append(")");
                this.sqlCommandPackage.parameterMap.put(beginParameterName, beginValue);
                this.sqlCommandPackage.parameterMap.put(endParameterName, endValue);
                return this.sqlCommandPackage;
            }

            /**
             * 封装 数据段互相包含（开区间 位置相同）条件
             *
             * @param minParameterName 最小值参数名
             * @param maxParameterName 最大值参数名
             * @param minValue         最小值
             * @param maxValue         最大值
             * @author LiuHuiYu
             * Created DateTime 2022-12-01 10:23
             */
            public <P> SqlCommandPackage<T> inclusion(String minParameterName, String maxParameterName, P minValue, P maxValue) {
                this.checkField2();
                if (minValue == null || maxValue == null) {
                    return this.sqlCommandPackage;
                }
                this.sqlCommandPackage.sqlBuilder.append(condition).append(" (");
                this.sqlCommandPackage.sqlBuilder.append("((").append(minFieldName).append(" < :").append(minParameterName).append(") and (")
                        .append(maxFieldName).append(" > :").append(minParameterName).append("))");
                this.sqlCommandPackage.sqlBuilder.append("or");
                this.sqlCommandPackage.sqlBuilder.append("((").append(minFieldName).append(" < :").append(maxParameterName).append(") and (")
                        .append(maxFieldName).append(" > :").append(maxParameterName).append("))");
                this.sqlCommandPackage.sqlBuilder.append("or");
                this.sqlCommandPackage.sqlBuilder.append("((").append(minFieldName).append(" >= :").append(minParameterName).append(") and (")
                        .append(maxFieldName).append(" <= :").append(maxParameterName).append("))");
                this.sqlCommandPackage.sqlBuilder.append(")");
                this.sqlCommandPackage.parameterMap.put(minParameterName, minValue);
                this.sqlCommandPackage.parameterMap.put(maxParameterName, maxValue);
                return this.sqlCommandPackage;
            }

            /**
             * 等于
             *
             * @param parameterName 参数名
             * @param value         值
             * @author LiuHuiYu
             * Created DateTime 2023-02-23 23:51
             */
            public <P> SqlCommandPackage<T> eq(String parameterName, P value) {
                return this.generate("=", parameterName, value);
            }

            /**
             * <> 比较
             *
             * @param parameterName 参数名称
             * @param value         值
             * @author LiuHuiYu
             * Created DateTime 2023-03-25 9:23
             */
            public <P> SqlCommandPackage<T> ne(String parameterName, P value) {
                return this.generate("<>", parameterName, value);
            }

            /**
             * > 比较
             *
             * @param parameterName 参数名称
             * @param value         值
             * @author LiuHuiYu
             * Created DateTime 2023-03-25 9:23
             */
            public <P> SqlCommandPackage<T> gt(String parameterName, P value) {
                return this.generate(">", parameterName, value);
            }

            /**
             * < 比较
             *
             * @param parameterName 参数名称
             * @param value         值
             * @author LiuHuiYu
             * Created DateTime 2023-03-25 9:23
             */
            public <P> SqlCommandPackage<T> lt(String parameterName, P value) {
                return this.generate("<", parameterName, value);
            }

            /**
             * >= 比较
             *
             * @param parameterName 参数名称
             * @param value         值
             * @author LiuHuiYu
             * Created DateTime 2023-03-25 9:23
             */
            public <P> SqlCommandPackage<T> ge(String parameterName, P value) {
                return this.generate(">=", parameterName, value);
            }

            /**
             * <= 比较
             *
             * @param parameterName 参数名称
             * @param value         值
             * @author LiuHuiYu
             * Created DateTime 2023-03-25 9:23
             */
            public <P> SqlCommandPackage<T> le(String parameterName, P value) {
                return this.generate("<=", parameterName, value);
            }

            /**
             * 为 null
             *
             * @author LiuHuiYu
             * Created DateTime 2023-03-25 9:23
             */
            public SqlCommandPackage<T> isNull() {
                this.checkField();
                this.sqlCommandPackage.sqlBuilder
                        .append(condition)
                        .append("(").append(this.fieldName)
                        .append(" is null )");
                return this.sqlCommandPackage;
            }

            /**
             * 为 null
             *
             * @author LiuHuiYu
             * Created DateTime 2023-03-25 9:23
             */
            public SqlCommandPackage<T> isNotNull() {
                this.checkField();
                this.sqlCommandPackage.sqlBuilder
                        .append(condition)
                        .append("(").append(this.fieldName)
                        .append(" is not null )");
                return this.sqlCommandPackage;
            }

            private <P> SqlCommandPackage<T> generate(String operator, String parameterName, P value) {
                this.checkField();
                if (value != null) {
                    this.sqlCommandPackage.sqlBuilder
                            .append(condition)
                            .append("(").append(this.fieldName)
                            .append(" ").append(operator)
                            .append(" :").append(parameterName).append(")");
                    this.sqlCommandPackage.parameterMap.put(parameterName, value);
                }
                return this.sqlCommandPackage;
            }
            /*
 *           eq 就是 equal等于
ne就是 not equal不等于
gt 就是 greater than大于
lt 就是 less than小于
ge 就是 greater than or equal 大于等于
le 就是 less than or equal 小于等于
in 就是 in 包含（数组）
isNull 就是 等于null
*between 就是 在2个条件之间(包括边界值)
*like就是 模糊查询
            * */
        }
    }
}
