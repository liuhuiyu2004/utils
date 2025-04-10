package com.liuhuiyu.jpa.sql;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 功能<p>
 * Created on 2025/4/10 20:41
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
class SelectSqlTest extends AbstractTest {
    @Test
    public void getSql01() {
        String sqlBase = "select t.a,t.b,t.c,t1.aa,t1.bb from user t left join bran t1 on t1.id=t.id";
        SelectSql selectSql = new SelectSql(sqlBase);
        selectSql.getSqlWhere().and("t.a").eq("1");
        selectSql.getSqlWhere().and("t.b").eq("2");
        selectSql.getOrderBy().asc("t.a").desc("t.b");
        selectSql.getGroupBy().addGroup("t.a").addGroup("t.b");
        selectSql.getSqlHaving().and("t.a").eq("1");
        LOG.info("{};\n{}", selectSql.getSql(), selectSql.getParameterList());
    }

}