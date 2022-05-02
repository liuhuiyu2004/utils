package com.liuhuiyu.jpa.oracle.util;

import com.liuhuiyu.dto.Paging;

import java.util.Arrays;
import java.util.Locale;

/**
 * dao工具类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-01-12 9:38
 */
public class OracleDaoUtil {
    /**
     * oracle分页查询
     *
     * @param sqlBuilder 原始sql
     * @param paging     分页信息
     * @author LiuHuiYu
     * Created DateTime 2022-01-12 9:39
     */
    public static void paginationOracleSql(StringBuilder sqlBuilder, Paging paging) {
        String rowNumName;
        for(int i=0;;i++){
            rowNumName = "rowno_" + i;
            if(!sqlBuilder.toString().toLowerCase(Locale.ROOT).contains(rowNumName)){
                break;
            }
        }
        sqlBuilder.insert(0, "SELECT * FROM (SELECT t_pagination.*, ROWNUM AS " + rowNumName + " FROM(");
        sqlBuilder.append(") t_pagination WHERE ROWNUM <= ")
                .append(paging.endRowNo())
                .append(") table_alias WHERE table_alias.")
                .append(rowNumName)
                .append(" >= ")
                .append(paging.beginRowNo());
    }

    /**
     * 将当前 sql查询语句转换成 统计数量的语句
     * 不支持复杂查询
     *
     * @param sqlBuilder sql 字符串的 StringBuilder
     * @author LiuHuiYu
     * Created DateTime 2022-01-12 9:57
     */
    public static void countOracleSql(StringBuilder sqlBuilder) {
        int index = sqlBuilder.toString().toLowerCase().indexOf(" from ");
        sqlBuilder.replace(0, index, "select count(1)");
    }

    /**
     * 将当前 sql查询语句转换成 统计数量的语句
     * 支持复杂查询（当前未开放，如果使用会出现错误）
     *
     * @param sqlBuilder sql 字符串的 StringBuilder
     * @author LiuHuiYu
     * @deprecated 未启用字符串分析
     * Created DateTime 2022-01-12 9:57
     */
    @Deprecated
    public static void countOracleSql2(StringBuilder sqlBuilder) {
        String fromString = " from ";
        int f = 0;
        int level = 0;
        for (int i = 0; i < sqlBuilder.length(); i++) {
            //字符串检测
            if (Arrays.asList('\'', '\"').contains(sqlBuilder.charAt(i))) {
                throw new RuntimeException("当前解析无法解析包含字符串的sql语句");
            }
            //包含检测
            if (sqlBuilder.charAt(i) == '(') {
                level++;
            }
            else if (sqlBuilder.charAt(i) == ')') {
                level--;
            }
            if (level < 0) {
                throw new RuntimeException("输入的sql语句异常");
            }

//            if (f == fromString.length() && level == 0) {
//                ;
//            }
        }
        int index = sqlBuilder.toString().toLowerCase().indexOf(" from ");
        sqlBuilder.replace(0, index, "select count(1)");
    }
}
