package com.liuhuiyu.jpa;

import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-22 9:12
 */
@Log4j2
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
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sql);
            }
            catch (SQLException throwable) {
                throw new RuntimeException("创建PreparedStatement异常", throwable);
            }
            callFunctions.accept(preparedStatement);
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
            if (parameterMap != null) {
                namedParameterStatement.fillParameters(preparedStatement, parameterMap);
            }
            log.info(namedParameterStatement.getSql());
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
}
