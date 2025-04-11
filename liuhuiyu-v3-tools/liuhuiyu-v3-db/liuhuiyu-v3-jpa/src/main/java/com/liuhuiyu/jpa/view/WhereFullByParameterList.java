package com.liuhuiyu.jpa.view;

import com.liuhuiyu.jpa.sql.SelectSql;

/**
 * 功能<p>
 * Created on 2025/4/10 22:53
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface WhereFullByParameterList<T> {
    /**
     * 功能描述 <p>
     * Created DateTime 2023-10-19 11:55
     *
     * @param whereDto  条件
     * @param selectSql sql语句
     */
    void full(T whereDto, SelectSql selectSql);
}
