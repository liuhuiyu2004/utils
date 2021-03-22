package com.liuhuiyu.jpa;

import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

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

    private Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        }
        catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    private PreparedStatement getPreparedStatement(String sql) {
        try {
            return getConnection().prepareStatement(sql);
        }
        catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    private ResultSet getResultSet(String sql, Map<String, Object> parameterMap) {
        try {
            NamedParameterStatement namedParameterStatement = new NamedParameterStatement(sql);
            PreparedStatement preparedStatement = this.getPreparedStatement(namedParameterStatement.getSql());
            namedParameterStatement.fillParameters(preparedStatement, parameterMap);
            log.info(namedParameterStatement.getSql());
            return preparedStatement.executeQuery();
        }
        catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    /**
     * 对象列表获取
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:05
     * @param sql 基本语句
     * @param parameterMap 参数表
     * @return java.util.List<java.lang.Object[]>
     */
    protected List<Object[]> getResultList(String sql, Map<String, Object> parameterMap) {
        return getResultList(sql, parameterMap, false);
    }
    /**
     * 对象列表获取
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:05
     * @param sql 基本语句
     * @param parameterMap 参数表
     * @param onlyFirst 仅第一行数据
     * @return java.util.List<java.lang.Object[]>
     */
    protected List<Object[]> getResultList(String sql, Map<String, Object> parameterMap, boolean onlyFirst) {
        ArrayList<Object[]> resList = new ArrayList<>();
        ResultSet resultSet = this.getResultSet(sql, parameterMap);
        try {
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Object[] objs = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    objs[i - 1] = (resultSet.getObject(i));
                }
                resList.add(objs);
                if (onlyFirst) {
                    return resList;
                }
            }
            return resList;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 对象列表获取
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:04
     * @param b DaoOperator
     * @param sql 基本语句
     * @param parameterMap 参数表
     * @return java.util.List<T>
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
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:02
     * @param b DaoOperator
     * @param sql 执行语句
     * @param parameterMap 参数表
     * @return java.util.Optional<T>
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
