package com.liuhuiyu.jpa.help.select;

/**
 * 条件
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/3/1 16:27
 */
public class Condition {
    private final String connectors;
    private final SelectSql selectSql;
    private final String fieldName1;

    public Condition(SelectSql selectSql, String connectors, String fieldName1) {
        this.connectors = connectors;
        this.selectSql = selectSql;
        this.fieldName1 = fieldName1;
    }

    public SelectSql eq(Object value) {
        selectSql.getParameterList().add(value);
        selectSql.getSqlWhere().append(connectors).append("(").append(fieldName1).append(" = ?").append(")");
        return selectSql;
    }
}
