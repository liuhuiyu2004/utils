package com.liuhuiyu.jpa_plus.conditions;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

/**
 * 功能<p>
 * Created on 2025/6/19 08:18
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
class SqlSelectWrapperTest extends AbstractTest {
    @Test
    public void testSql() {
        SqlSelectWrapper sqlSelectWrapper = new SqlSelectWrapper("select * from user");
        sqlSelectWrapper.getOrderBy().addAsc("id");
        sqlSelectWrapper.getOrderBy().addDesc("name");
        sqlSelectWrapper.getGroupBy().add("id");
        sqlSelectWrapper.getGroupBy().add("name");
        sqlSelectWrapper.getWhere().and();
        LOG.info(sqlSelectWrapper.getSql());
    }
}