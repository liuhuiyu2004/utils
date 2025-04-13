package com.liuhuiyu.jpa.mysql.util;

import com.liuhuiyu.dto.Paging;

import java.util.Locale;

/**
 * 功能<p>
 * Created on 2025/4/13 21:09
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class MySqlDaoUtil {
    /**
     * Oracle分页查询
     *
     * @param sqlBuilder 原始sql
     * @param paging     分页信息
     * Created DateTime 2022-01-12 9:39
     */
    public static void paginationMySql(StringBuilder sqlBuilder, Paging paging) {
        sqlBuilder.append(" limit ").append(paging.beginRowNo()).append(",").append(paging.getPageSize());
    }
}
