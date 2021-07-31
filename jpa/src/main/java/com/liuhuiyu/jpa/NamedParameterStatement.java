package com.liuhuiyu.jpa;

import lombok.extern.log4j.Log4j2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-22 11:01
 */
public class NamedParameterStatement {
    private final Map<Integer, String> paramsMap = new HashMap<>();
    private String sql = "";

    public Map<Integer, String> getParamsMap() {
        return paramsMap;
    }

    public String getSql() {
        return this.sql;
    }

    private void emptyMap() {
        paramsMap.clear();
    }

    public NamedParameterStatement(String sql) {
        this.parseSql(sql);
    }

    /**
     * 分析处理带命名参数的SQL语句。使用Map存储参数，然后将参数替换成?
     *
     * @param sql 基本语句
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:10
     */
    private void parseSql(String sql) {
        String regex = "(:(\\w+))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
        emptyMap();
        int idx = 1;
        while (m.find()) {
            //参数名称可能有重复，使用序号来做Key
            paramsMap.put(idx++, m.group(2));
        }
        this.sql = sql.replaceAll(regex, "?");
        Log.i("分析前：" + sql);
        Log.d("分析后：" + this.sql);
    }

    /**
     * 使用参数值Map，填充pStat
     *
     * @param pStat PreparedStatement
     * @param pMap  命名参数的值表，其中的值可以比较所需的参数多。
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:10
     */
    public void fillParameters(PreparedStatement pStat, Map<String, Object> pMap) {
        this.paramsMap.forEach((idKey, valueKey) -> {
            if (pMap.containsKey(valueKey)) {
                Object value = pMap.get(valueKey);
                try {
                    pStat.setObject(idKey, value);
                }
                catch (SQLException throwables) {
                    throw new RuntimeException("填充参数出错，原因：", throwables);
                }
            }
            else {
                throw new RuntimeException("语句中参数‘" + valueKey + "’不存在列表中");
            }
        });
    }
}
