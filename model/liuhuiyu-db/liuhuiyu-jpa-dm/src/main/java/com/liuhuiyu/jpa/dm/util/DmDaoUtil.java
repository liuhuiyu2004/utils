package com.liuhuiyu.jpa.dm.util;

import com.liuhuiyu.dto.Paging;

import java.util.Locale;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 16:45
 */
public class DmDaoUtil {
    /**
     * 达蒙分页查询
     *
     * @param sqlBuilder 原始sql
     * @param paging     分页信息
     * @author LiuHuiYu
     * Created DateTime 2022-01-12 9:39
     */
    public static void paginationOracleSql(StringBuilder sqlBuilder, Paging paging) {
        String rowNumName;
        for (int i = 0; ; i++) {
            rowNumName = "rowno_" + i;
            if (!sqlBuilder.toString().toLowerCase(Locale.ROOT).contains(rowNumName)) {
                break;
            }
        }
        sqlBuilder.insert(0, "select * from (");
        sqlBuilder.append(") ").append(rowNumName).append(" limit ").append(paging.beginRowNo() - 1).append(",").append(paging.getPageSize());
    }
}
