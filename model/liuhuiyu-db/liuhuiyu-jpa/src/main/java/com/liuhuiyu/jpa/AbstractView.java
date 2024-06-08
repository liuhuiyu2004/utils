package com.liuhuiyu.jpa;

import com.liuhuiyu.core.lang.function_interface.ObjectToT;
import com.liuhuiyu.jpa.sql.WhereFullByParameterList;
import com.liuhuiyu.util.DataBaseUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * Created DateTime 2021-03-22 9:12
 * @version v1.0.0.0
 * @deprecated 使用AbstractTransactionalView 进行替代
 */
@Deprecated
public abstract class AbstractView {
    //region 基本功能
    private final DataSource dataSource;

    public AbstractView(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 连接获取
     *
     * @param callFunctions 调用函数
     *                      Created DateTime 2021-03-25 10:42
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
     *                      Created DateTime 2021-03-25 10:45
     */
    @SuppressWarnings("all")
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
     *                      Created DateTime 2021-03-25 10:46
     */
    protected void fullResultSet(String sql, List<Object> parameterMap, Consumer<ResultSet> callFunctions) {
        this.actionPreparedStatement(sql, preparedStatement -> {
            if (parameterMap != null && !parameterMap.isEmpty()) {
                DataBaseUtil.fillParameters(preparedStatement, parameterMap);
            }
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
    //endregion

    //region 无解析列表获取

    /**
     * 对象列表获取
     *
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @return List  数组对
     * Created DateTime 2021-03-22 14:05
     */
    protected List<Object[]> getResultObjectList(String sql, List<Object> parameterMap) {
        return this.getResultObjectList(sql, parameterMap, false);
    }

    /**
     * 对象列表获取
     *
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @param onlyFirst    仅第一行数据
     * @return List 对象数组
     * Created DateTime 2021-03-22 14:05
     */
    protected List<Object[]> getResultObjectList(String sql, List<Object> parameterMap, boolean onlyFirst) {
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
    //endregion

    //region 列表数据获取

    /**
     * 对象列表获取
     *
     * @param b            DaoOperator
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @param <T>          T
     * @return java.util.List<T> 返回
     * Created DateTime 2021-03-22 14:04
     */
    protected <T> List<T> getResultList(ObjectToT<T> b, String sql, List<Object> parameterMap) {
        List<?> list = this.getResultObjectList(sql, parameterMap);
        List<T> resList = new ArrayList<>();
        for (Object obj : list) {
            resList.add(b.objectToT(obj));
        }
        return resList;
    }

    /**
     * 对象列表获取
     *
     * @param sql          基本语句
     * @param parameterMap 参数表
     * @param onlyFirst    仅第一行数据
     * @return java.util.List<java.lang.Object [ ]>
     * Created DateTime 2021-03-22 14:05
     */
    protected <T> List<T> getResultListT(Class<T> clazz, String sql, List<Object> parameterMap, boolean onlyFirst) {
        ArrayList<T> resList = new ArrayList<>();
        this.fullResultSet(sql, parameterMap, (resultSet) -> {
            try {
                while (resultSet.next()) {
                    final T t = DataBaseUtil.rowToT(resultSet, clazz);
                    resList.add(t);
                }
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return resList;
    }

    /**
     * 快速 sql 查询
     *
     * @param input     查询入参
     * @param sql       原始语句
     * @param whereFull 查询填充
     * @param b         DaoOperator
     * @return java.util.List<R>
     * Created DateTime 2022-01-12 10:16
     */
    protected <T, R> List<R> selectList(T input, String sql, WhereFullByParameterList<T> whereFull, ObjectToT<R> b) {
        List<Object> parameterMap = new ArrayList<>(1);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(sql);
        sqlBuilder.append(" WHERE(1=1)");
        whereFull.full(input, sqlBuilder, parameterMap);
        return this.getResultList(b, sqlBuilder.toString(), parameterMap);
    }

    /**
     * 快速 sql 查询
     *
     * @param input     查询入参
     * @param sql       原始语句
     * @param whereFull 查询填充
     * @param clazz     返回类型
     * @return java.util.List<R>
     * Created DateTime 2022-01-12 10:16
     */
    protected <T, R> List<R> selectListT(T input, String sql, WhereFullByParameterList<T> whereFull, Class<R> clazz) {
        List<Object> parameterMap = new ArrayList<>(1);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(sql);
        sqlBuilder.append(" WHERE(1=1)");
        whereFull.full(input, sqlBuilder, parameterMap);
        return this.getResultListT(clazz, sqlBuilder.toString(), parameterMap, false);
    }

    /**
     * 返回第一行信息
     *
     * @param input     查询条件
     * @param sql       原始语句
     * @param whereFull 条件生成
     * @param clazz     结果转化
     * @return java.util.Optional<R>
     * Created DateTime 2022-05-09 16:16
     */
    protected <T, R> Optional<R> getFirstResultT(T input, String sql, WhereFullByParameterList<T> whereFull, Class<R> clazz) {
        List<R> list = this.selectListT(input, sql, whereFull, clazz);
        return list.stream().findFirst();
    }

    /**
     * 返回第一行信息
     *
     * @param input     查询条件
     * @param sql       原始语句
     * @param whereFull 条件生成
     * @param b         结果转化
     * @return java.util.Optional<R>
     * Created DateTime 2022-05-09 16:16
     */
    protected <T, R> Optional<R> getFirstResult(T input, String sql, WhereFullByParameterList<T> whereFull, ObjectToT<R> b) {
        List<R> list = this.selectList(input, sql, whereFull, b);
        return list.stream().findFirst();
    }
    //endregion

    //region 返回第一行信息

    /**
     * 返回第一行信息
     *
     * @param b            DaoOperator
     * @param sql          执行语句
     * @param parameterMap 参数表
     * @return java.util.Optional<T>
     * Created DateTime 2021-03-22 14:02
     */
    protected <T> Optional<T> getFirstResult(ObjectToT<T> b, String sql, List<Object> parameterMap) {
        List<?> list = this.getResultObjectList(sql, parameterMap, true);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        else {
            T t = b.objectToT(list.get(0));
            return Optional.of(t);
        }
    }

    /**
     * 返回第一行信息
     *
     * @param clazz        返回类型
     * @param sql          执行语句
     * @param parameterMap 参数表
     * @return java.util.Optional<T>
     * Created DateTime 2021-03-22 14:02
     */
    protected <T> Optional<T> getFirstResultT(Class<T> clazz, String sql, List<Object> parameterMap) {
        List<T> list = this.getResultListT(clazz, sql, parameterMap, true);
        return list.stream().findFirst();
    }

    /**
     * 统计查询
     *
     * @param sql          sql语句
     * @param parameterMap 参数
     * @return java.lang.Long
     * Created DateTime 2022-02-15 16:54
     */
    protected Long selectCount(String sql, List<Object> parameterMap) {
        ObjectToT<Long> longDaoOperator = (o) -> {
            Object obj = o.getClass().isArray() ? ((Object[]) o)[0] : o;
            return obj instanceof Number ? ((Number) obj).longValue() : 0L;
        };
        return getFirstResult(longDaoOperator, sql, parameterMap).orElse(0L);
    }
    //endregion

    //region 第一行，第一列信息获取

    /**
     * 第一行第一列信息获取
     *
     * @param sql          语句
     * @param parameterMap 参数表
     * @return java.util.Optional<java.lang.Object>
     * Created DateTime 2021-03-22 13:46
     */
    protected Optional<Object> getSingleResult(String sql, List<Object> parameterMap) {
        List<Object[]> list = this.getResultObjectList(sql, parameterMap, true);
        if (list.isEmpty() || list.get(0).length == 0) {
            return Optional.empty();
        }
        else {
            return Optional.of(list.get(0)[0]);
        }
    }


    /**
     * 统计查询
     *
     * @param input     查询入参
     * @param sql       原始语句
     * @param whereFull 查询填充
     * @return java.lang.Long
     * Created DateTime 2022-01-12 10:48
     */
    protected <T> Long selectCount(T input, String sql, WhereFullByParameterList<T> whereFull) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
//        sqlBuilder.append(" WHERE(1=1)");
        List<Object> parameterMap = new ArrayList<>(1);
        whereFull.full(input, sqlBuilder, parameterMap);
        final Optional<Object> singleResult = this.getSingleResult(sqlBuilder.toString(), parameterMap);
        return singleResult.map(o -> ((BigDecimal) o).longValue()).orElse(0L);
    }
    //endregion
}
