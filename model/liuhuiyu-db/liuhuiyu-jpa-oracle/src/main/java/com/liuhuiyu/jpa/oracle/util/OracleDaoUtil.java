package com.liuhuiyu.jpa.oracle.util;

import com.liuhuiyu.dto.Paging;

import java.util.Locale;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 16:45
 */
public class OracleDaoUtil {
    /**
     * Oracle分页查询
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
}
