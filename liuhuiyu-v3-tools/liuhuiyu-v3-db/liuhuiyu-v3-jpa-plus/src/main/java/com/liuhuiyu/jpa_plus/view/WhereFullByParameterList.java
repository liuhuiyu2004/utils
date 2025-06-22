package com.liuhuiyu.jpa_plus.view;

import com.liuhuiyu.jpa_plus.conditions.SqlSelectWrapper;

/**
 * 功能<p>
 * Created on 2025/6/22 11:52
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface WhereFullByParameterList<T> {

    /**
     * 根据条件完善填充select语句<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/6/22 11:52
     *
     * @param whereDto  条件
     * @param selectSql sql语句
     */
    void full(T whereDto, SqlSelectWrapper selectSql);
}
