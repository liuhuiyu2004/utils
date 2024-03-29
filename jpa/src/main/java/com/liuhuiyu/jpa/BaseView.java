package com.liuhuiyu.jpa;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-22 9:12
 */
public abstract class BaseView {
    private final DataSource dataSource;

    public BaseView(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 连接获取
     *
     * @param callFunctions 调用函数
     * @author LiuHuiYu
     * Created DateTime 2021-03-25 10:42
     */
    protected void actionConnection(Consumer<Connection> callFunctions) {
        try (Connection connection = this.dataSource.getConnection()) {
            callFunctions.accept(connection);
        }
        catch (SQLException throwable) {
            throw new RuntimeException("获取连接异常。", throwable);
        }
    }

    /**
     * 获取 PreparedStatement
     *
     * @param sql           基础语句
     * @param callFunctions 回调
     * @author LiuHuiYu
     * Created DateTime 2021-03-25 10:45
     */
    protected void actionPreparedStatement(String sql, Consumer<PreparedStatement> callFunctions) {
        actionConnection((connection) -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                callFunctions.accept(preparedStatement);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 执行sql回调ResultSet
     *
     * @param sql           基础语句
     * @param parameterMap  参数Map
     * @param callFunctions 回调
     * @author LiuHuiYu
     * Created DateTime 2021-03-25 10:46
     */
    protected void fullResultSet(String sql, Map<String, Object> parameterMap, Consumer<ResultSet> callFunctions) {
        NamedParameterStatement namedParameterStatement = new NamedParameterStatement(sql);
        this.actionPreparedStatement(namedParameterStatement.getSql(), preparedStatement -> {
            if (parameterMap != null && parameterMap.size() > 0) {
                namedParameterStatement.fillParameters(preparedStatement, parameterMap);
            }
            Log.i(namedParameterStatement.getSql());
            ResultSet resultSet;
            try {
                resultSet = preparedStatement.executeQuery();
            }
            catch (SQLException throwable) {
                throw new RuntimeException("执行sql语句异常。", throwable);
            }
            callFunctions.accept(resultSet);
        });
    }


    /**
     * 对象列表获取
     *
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @return java.util.List<java.lang.Object [ ]>
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:05
     */
    protected List<Object[]> getResultList(String sql, Map<String, Object> parameterMap) {
        return getResultList(sql, parameterMap, false);
    }

    /**
     * 对象列表获取
     *
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @param onlyFirst    仅第一行数据
     * @return java.util.List<java.lang.Object [ ]>
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:05
     */
    protected List<Object[]> getResultList(String sql, Map<String, Object> parameterMap, boolean onlyFirst) {
        ArrayList<Object[]> resList = new ArrayList<>();
        this.fullResultSet(sql, parameterMap, (resultSet) -> {
            try {
                int columnCount = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()) {
                    Object[] objs = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        objs[i - 1] = (resultSet.getObject(i));
                    }
                    resList.add(objs);
                    if (onlyFirst) {
                        return;
                    }
                }
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return resList;
    }

    /**
     * 对象列表获取
     *
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @param onlyFirst    仅第一行数据
     * @return java.util.List<java.lang.Object [ ]>
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:05
     */
    protected <T> List<T> getResultListT(Class<T> clazz, String sql, Map<String, Object> parameterMap, boolean onlyFirst) {
        ArrayList<T> resList = new ArrayList<>();
        this.fullResultSet(sql, parameterMap, (resultSet) -> {
            try {
                int columnCount = resultSet.getMetaData().getColumnCount();
//                ArrayList<String> columnLabels=this.setColumnLabels(resultSet.getMetaData());
                while (resultSet.next()) {
                    while (resultSet.next()) {
                        final T t = rowToT(resultSet, clazz);
                        resList.add(t);
                    }
                }
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return resList;
    }


//    /**
//     * 字段名称按照顺序设置
//     *
//     * @author LiuHuiYu
//     * Created DateTime 2023-07-28 17:26
//     */
//    private ArrayList<String> setColumnLabels(ResultSetMetaData metaData) {
//        ArrayList<String> columnLabels;
//        try {
//            int count = metaData.getColumnCount();
//            columnLabels = new ArrayList<>(count);
//            for (int i = 1; i <= count; i++) {
//                columnLabels.add(metaData.getColumnLabel(i).toUpperCase());
//            }
//        }
//        catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//        return columnLabels;
//    }

    /**
     * 对象列表获取
     *
     * @param b            DaoOperator
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @return java.util.List<T>
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:04
     */
    protected <T> List<T> getResultList(DaoOperator<T> b, String sql, Map<String, Object> parameterMap) {
        List<?> list = this.getResultList(sql, parameterMap);
        List<T> resList = new ArrayList<>();
        for (Object obj : list) {
            resList.add(b.objectToT(obj));
        }
        return resList;
    }

    /**
     * 返回第一行信息
     *
     * @param b            DaoOperator
     * @param sql          执行语句
     * @param parameterMap 参数表
     * @return java.util.Optional<T>
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:02
     */
    protected <T> Optional<T> getFirstResult(DaoOperator<T> b, String sql, Map<String, Object> parameterMap) {
        List<?> list = this.getResultList(sql, parameterMap, true);
        if (list.size() == 0) {
            return Optional.empty();
        }
        else {
            T t = b.objectToT(list.get(0));
            return Optional.of(t);
        }
    }

    /**
     * 第一行第一列信息获取
     *
     * @param sql          语句
     * @param parameterMap 参数表
     * @return java.util.Optional<java.lang.Object>
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 13:46
     */
    protected Optional<Object> getSingleResult(String sql, Map<String, Object> parameterMap) {
        List<Object[]> list = this.getResultList(sql, parameterMap, true);
        if (list.size() == 0 || list.get(0).length == 0) {
            return Optional.empty();
        }
        else {
            return Optional.of(list.get(0)[0]);
        }
    }

    /**
     * 快速 sql 查询
     *
     * @param input     查询入参
     * @param sql       原始语句
     * @param whereFull 查询填充
     * @param b         DaoOperator
     * @return java.util.List<R>
     * @author LiuHuiYu
     * Created DateTime 2022-01-12 10:16
     */
    protected <T, R> List<R> selectList(T input, String sql, WhereFull<T> whereFull, DaoOperator<R> b) {
        Map<String, Object> parameterMap = new HashMap<>(1);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(sql);
        sqlBuilder.append(" WHERE(1=1)");
        whereFull.full(input, sqlBuilder, parameterMap);
        return this.getResultList(b, sqlBuilder.toString(), parameterMap);
    }

    /**
     * 统计查询
     *
     * @param input     查询入参
     * @param sql       原始语句
     * @param whereFull 查询填充
     * @return java.lang.Long
     * @author LiuHuiYu
     * Created DateTime 2022-01-12 10:48
     */
    protected <T> Long selectCount(T input, String sql, WhereFull<T> whereFull) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(" WHERE(1=1)");
        Map<String, Object> parameterMap = new HashMap<>(1);
        whereFull.full(input, sqlBuilder, parameterMap);
        final Optional<Object> singleResult = this.getSingleResult(sqlBuilder.toString(), parameterMap);
        return singleResult.map(o -> ((BigDecimal) o).longValue()).orElse(0L);
    }

    /**
     * 返回第一行信息
     *
     * @param input     查询条件
     * @param sql       原始语句
     * @param whereFull 条件生成
     * @param b         结果转化
     * @return java.util.Optional<R>
     * @author LiuHuiYu
     * Created DateTime 2022-05-09 16:16
     */
    protected <T, R> Optional<R> getFirstResult(T input, String sql, WhereFull<T> whereFull, DaoOperator<R> b) {
        List<R> list = this.selectList(input, sql, whereFull, b);
        if (list.size() == 0) {
            return Optional.empty();
        }
        else {
            return Optional.of(list.get(0));
        }
    }

    /**
     * 统计查询
     *
     * @param sql          sql语句
     * @param parameterMap 参数
     * @return java.lang.Long
     * @author LiuHuiYu
     * Created DateTime 2022-02-15 16:54
     */
    protected Long selectCount(String sql, Map<String, Object> parameterMap) {
        DaoOperator<Long> longDaoOperator = (o) -> {
            Object obj = o.getClass().isArray() ? ((Object[]) o)[0] : o;
            return obj instanceof Number ? ((Number) obj).longValue() : 0L;
        };
        return getFirstResult(longDaoOperator, sql, parameterMap).orElse(0L);
    }

    private <T> T rowToT(ResultSet rs, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
            final Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                int columnIndex ;
                try {
                    columnIndex = rs.findColumn(field.getName());
                }
                catch (Exception igex) {
                    columnIndex = -1;
                }
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod) ||
                        columnIndex < 0) {
                    continue;
                }
                Object rowValue = rs.getObject(columnIndex + 1);
                Object value = getValue(field, rowValue);
                field.setAccessible(true);
                field.set(obj, value);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将指定值转换为与类字段相同类型的值
     *
     * @param field 字段
     * @param value 值
     * @return java.lang.Object
     * @author LiuHuiYu
     * Created DateTime 2023-07-28 16:19
     */
    private static Object getValue(Field field, Object value) {
        if (value == null) {
            return null;
        }
        if (field.getType().getName().equals(String.class.getName())) {
            return value.toString();
        }
        if (value instanceof BigDecimal) {
            BigDecimal v = (BigDecimal) value;
            if (field.getType().getName().equals(int.class.getName()) ||
                    field.getType().getName().equals(Integer.class.getName())) {
                return v.intValue();
            }
            else if (field.getType().getName().equals(long.class.getName()) ||
                    field.getType().getName().equals(Long.class.getName())) {
                return v.longValue();
            }
            else if (field.getType().getName().equals(float.class.getName()) ||
                    field.getType().getName().equals(Float.class.getName())) {
                return v.floatValue();
            }
            else if (field.getType().getName().equals(double.class.getName()) ||
                    field.getType().getName().equals(Double.class.getName())) {
                return v.doubleValue();
            }
            else if (field.getType().getName().equals(byte.class.getName()) ||
                    field.getType().getName().equals(Byte.class.getName())) {
                return v.byteValue();
            }
            else if (field.getType().getName().equals(short.class.getName()) ||
                    field.getType().getName().equals(Short.class.getName())) {
                return v.shortValue();
            }
        }
        return value;
    }

    /**
     * 查询建造者
     *
     * @author LiuHuiYu
     * Created DateTime 2022-05-09 16:18
     */
    public class SelectBuilder<T, R> {
        private final String sql;
        private final DaoOperator<R> b;

        private Map<String, Object> parameterMap;
        private T input;
        private WhereFull<T> whereFull;

        /**
         * 建议 数量查询 使用
         *
         * @param sql 原始语句
         * @author LiuHuiYu
         * Created DateTime 2022-05-09 16:21
         */
        public SelectBuilder(String sql) {
            this.b = null;
            this.sql = sql;
        }


        /**
         * 建议查询sql结果的时候使用
         *
         * @param daoOperator 解析函数
         * @param sql         原生语句
         * @author LiuHuiYu
         * Created DateTime 2022-05-09 16:22
         */
        public SelectBuilder(DaoOperator<R> daoOperator, String sql) {
            this.b = daoOperator;
            this.sql = sql;
        }

        /**
         * 设置 参数列表
         *
         * @param parameterMap 参数列表
         * @return com.liuhuiyu.jpa.BaseView.SelectBuilder<T, R>
         * @author LiuHuiYu
         * Created DateTime 2022-05-09 15:51
         */
        public SelectBuilder<T, R> parameterMap(Map<String, Object> parameterMap) {
            if (this.input != null || this.whereFull != null) {
                throw new RuntimeException("此方法与 whereFull 方法互斥不能同时使用");
            }
            this.parameterMap = parameterMap;
            return this;
        }

        /**
         * 设置条件查询解析
         *
         * @param input     入参
         * @param whereFull 解析条件
         * @return com.liuhuiyu.jpa.BaseView.SelectBuilder<T, R>
         * @author LiuHuiYu
         * Created DateTime 2022-05-09 15:51
         */
        public SelectBuilder<T, R> whereFull(T input, WhereFull<T> whereFull) {
            if (this.parameterMap != null) {
                throw new RuntimeException("此方法与 parameterMap 方法互斥不能同时使用");
            }
            this.input = input;
            this.whereFull = whereFull;
            return this;
        }

        /**
         * 统计查询
         *
         * @return java.lang.Long
         * @author LiuHuiYu
         * Created DateTime 2022-05-09 16:12
         */
        public Long buildCount() {
            if (input == null) {
                return selectCount(sql, parameterMap);
            }
            else {
                return selectCount(input, sql, whereFull);
            }
        }

        /**
         * 列表查询
         *
         * @return java.util.List<R>
         * @author LiuHuiYu
         * Created DateTime 2022-05-09 16:12
         */
        public List<R> buildList() {
            if (b == null) {
                throw new NullPointerException("缺少转换函数");
            }
            if (input == null) {
                return getResultList(this.b, this.sql, this.parameterMap);
            }
            else {
                return selectList(input, sql, whereFull, b);
            }
        }

        /**
         * 第一条记录查询
         *
         * @return java.util.Optional<R>
         * @author LiuHuiYu
         * Created DateTime 2022-05-09 16:12
         */

        public Optional<R> buildFirst() {
            if (b == null) {
                throw new NullPointerException("缺少转换函数");
            }
            if (input == null) {
                return getFirstResult(this.b, this.sql, this.parameterMap);
            }
            else {
                return getFirstResult(input, sql, whereFull, b);
            }
        }
    }
}
