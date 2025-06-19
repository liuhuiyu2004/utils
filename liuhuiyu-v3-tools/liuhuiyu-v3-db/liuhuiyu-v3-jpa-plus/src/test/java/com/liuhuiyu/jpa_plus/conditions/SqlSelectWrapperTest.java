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
        sqlSelectWrapper.getWhere()
                .and().ge("t.ge", 1)
                .and().gt("t.gt", 1)
                .and().le("t.le", 1)
                .and().lt("t.lt", 1)
                .and().eq("t.eq", 1)
                .and().ne("t.ne", 1)
                .and().between("t.between", 1, 11)
                .and().inclusion("t.b1", "t.b2", 1, 11)
                .and().likeValue("t.like", "ak")
                .and().ne("t.ne", 1);
        sqlSelectWrapper.getHaving().and().eq("t.id", 2)
                .and().ne("t.id", 3);
        LOG.info(sqlSelectWrapper.getSql());
    }
}