package com.liuhuiyu.jpa.help.select2;

import java.util.List;

/**
 * sql表达式载体接口（where\having）
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/3/2 8:35
 */
public interface ExpressionZt {
    /**
     * 基本sql表达式<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/2 8:42
     *
     * @return java.lang.StringBuilder
     */
    StringBuilder getSqlZtBuilder();

    /**
     * 基本表达式参数值<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/2 8:43
     *
     * @return java.util.List<java.lang.Object>
     */
    List<Object> getSqlZtParameters();

    /**
     * and表达式<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/2 8:43
     *
     * @param fieldName 字段名
     * @return com.liuhuiyu.jpa.help.select2.ExpressionZt
     */
    SqlExpression andExpression(String fieldName);

    /**
     * or表达式<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/9 9:47
     *
     * @param fieldName 字段名
     * @return com.liuhuiyu.jpa.help.select2.SqlExpression
     */
    SqlExpression orExpression(String fieldName);

    /**
     * 固定表达式<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/9 9:46
     *
     * @param constExpression 固定表达式
     * @return com.liuhuiyu.jpa.help.select2.ExpressionZt
     */
    ExpressionZt andConst(String constExpression);

    /**
     * 固定表达式<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/9 9:46
     *
     * @param constExpression 固定表达式
     * @return com.liuhuiyu.jpa.help.select2.ExpressionZt
     */
    ExpressionZt orConst(String constExpression);

    /**
     * 获取连接符<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/3/2 8:43
     *
     * @return java.lang.String
     */
    String getConnectors();
}
