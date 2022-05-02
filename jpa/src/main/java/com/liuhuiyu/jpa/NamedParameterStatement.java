package com.liuhuiyu.jpa;

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
    private String sql;

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
        this.sql = this.parseSql2(sql);
    }

    /**
     * 分析处理带命名参数的SQL语句。使用Map存储参数，然后将参数替换成?
     *
     * @param sql 基本语句
     * @author LiuHuiYu
     * Created DateTime 2021-03-22 14:10
     */
    @Deprecated
    private void parseSql(String sql) {
        String regex = "((=[\\s+]|=):(\\w+))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
        emptyMap();
        int idx = 1;
        while (m.find()) {
            //参数名称可能有重复，使用序号来做Key
            paramsMap.put(idx++, m.group(m.groupCount()));
        }
        this.sql = sql.replaceAll(regex, "=?");
        Log.i("分析前：" + sql);
        Log.d("分析后：" + this.sql);
    }

    private String parseSql2(String query) {
        int length = query.length();
        StringBuilder parsedQuery = new StringBuilder(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;

        for (int i = 0; i < length; i++) {
            char c = query.charAt(i);
            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            }
            else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            }
            else {
                if (c == '\'') {
                    inSingleQuote = true;
                }
                else if (c == '"') {
                    inDoubleQuote = true;
                }
                else if (c == ':' &&
                        i + 1 < length &&
                        Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name = query.substring(i + 1, j);
                    // 用问号替换参数
                    c = '?';
                    // 如果参数是跳过结尾
                    i += name.length();
                    this.paramsMap.put(index++, name);
                }
            }
            parsedQuery.append(c);
        }
        return parsedQuery.toString();
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
