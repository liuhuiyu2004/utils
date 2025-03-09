package com.liuhuiyu.jpa.help.select2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/3/1 16:43
 */
public class SqlWhere implements ExpressionZt {
    List<Object> parameterList = new ArrayList<>();
    StringBuilder sqlWhere = new StringBuilder();
    String connectors;
    @Override
    public SqlExpression andExpression(String fieldName) {
        this.connectors="AND";
        return new SqlExpression(this, fieldName);
    }
    @Override
    public SqlExpression orExpression(String fieldName) {
        this.connectors="AND";
        return new SqlExpression(this, fieldName);
    }
    /**
     * 固定表达式条件<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/9 9:42
     * @param constExpression 待添加的表达式
     * @return com.liuhuiyu.jpa.help.select2.SqlWhere
     */
    @Override
    public SqlWhere andConst(String constExpression) {
        createSqlWhere("AND(").sqlWhere.append(constExpression).append(")");
        return this;
    }
    /**
     * 固定表达式条件<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/9 9:42
     * @param constExpression 待添加的表达式
     * @return com.liuhuiyu.jpa.help.select2.SqlWhere
     */
    @Override
    public SqlWhere orConst(String constExpression) {
        createSqlWhere("OR").sqlWhere.append(constExpression);
        return this;
    }

    public SqlWhere() {
    }

    /**
     * 使用常量表达式进行对where进行初始化
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/2 11:33
     *
     * @param constExpression 常量表达式
     */
    public SqlWhere(String constExpression) {
        this.sqlWhere = new StringBuilder("(")
                .append(constExpression)
                .append(")");
    }

    /**
     * 创建where关联<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/2 11:36
     *
     * @param connectors 连接符
     */
    public SqlWhere createSqlWhere(String connectors) {
        if (!sqlWhere.isEmpty()) {
            sqlWhere.append(connectors);
        }
        return this;
    }

    @Override
    public StringBuilder getSqlZtBuilder() {
        if (!this.sqlWhere.isEmpty()) {
            this.sqlWhere.insert(0, "WHERE ");
        }
        return this.sqlWhere;
    }

    @Override
    public List<Object> getSqlZtParameters() {
        return this.parameterList;
    }

    public String getConnectors() {
        return connectors;
    }
}
