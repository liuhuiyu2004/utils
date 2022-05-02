package com.liuhuiyu.jpa;


import javax.sql.DataSource;
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
        actionConnection(connection -> {
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
//        final Optional<Long> firstResult = getFirstResult(longDaoOperator, sql, parameterMap);
//        return firstResult.orElse(0L);
    }
}
