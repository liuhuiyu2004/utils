package com.liuhuiyu.jpa.oracle.dao;

import com.liuhuiyu.dto.IPaging;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 8:56
 */
public class OracleBaseViewDemo extends OracleBaseView {

    public static OracleBaseViewDemo getInstance() {
        return new OracleBaseViewDemo(new AbstractDataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                throw new SQLException("");
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                throw new SQLException("");
            }
        });
    }

    public OracleBaseViewDemo(DataSource dataSource) {
        super(dataSource);
    }

    public List<Object> findList(IPaging pagingFind) {
        return createPageImplBuilder(pagingFind).buildList();
    }


    public PageImpl<Object> findPage(IPaging pagingFind) {
        return new PageImplBuilder<>((o) -> new Object(), pagingFind, "SELECT * FROM AA T", this::fullWhere)
                .setCountSql("SELECT count(0) FROM AA T")
                .baseWhere("")
                .order("").buildPage();
    }

    public PageImpl<Object> findPage2(IPaging pagingFind) {
        return super.page((o) -> new Object(), pagingFind, "SELECT * FROM AA T", "WHERE(1=1)", "ORDER T.C", this::fullWhere);
    }

    public PageImpl<Object> findPage3(IPaging pagingFind) {
        return super.page((o) -> new Object(), pagingFind, "SELECT * FROM BB T", "WHERE(T.C=2)", "ORDER T.B", this::fullWhere);
    }

    public Long findCount(IPaging pagingFind) {
        return createPageImplBuilder(pagingFind).buildCount();
    }

    public Long findCount2(IPaging pagingFind) {
        return super.count(pagingFind, "SELECT * from AA T", "WHERE(1=1)", this::fullWhere);
    }

    private PageImplBuilder<IPaging, Object> createPageImplBuilder(IPaging pagingFind) {
        return new PageImplBuilder<>((o) -> new Object(), pagingFind, "", this::fullWhere)
                .baseWhere("")
                .order("");
    }

    void fullWhere(IPaging pagingFind, StringBuilder sqlBuilder, Map<String, Object> parameterMap) {
        OracleBaseViewTestSql oracleBaseViewTestSql = new OracleBaseViewTestSql(pagingFind, sqlBuilder, parameterMap);
        oracleBaseViewTestSql.commandPackage();
    }

    static class OracleBaseViewTestSql extends SqlCommandPackage<Object> {

        /**
         * SQL原生封装 构建函数
         *
         * @param findDto      查询条件
         * @param sqlBuilder   sqlBuilder
         * @param parameterMap 参数
         * @author LiuHuiYu
         * Created DateTime 2022-11-20 8:27
         */
        public OracleBaseViewTestSql(Object findDto, StringBuilder sqlBuilder, Map<String, Object> parameterMap) {
            super(findDto, sqlBuilder, parameterMap);
        }

        private void like0() {
            super.likeValue("find0", "t.field0", "ab");
        }

        private void like1() {
            super.likeValue("find1", "t.field1", "abc");
        }

        private void like2() {
            super.likeValue("find2", "t.field2", "abcd", false, false, false);
        }

        private void in0() {
            super.inPackage("in1", "t.fieldIn1", new String[]{"a"}, false, false);
        }

        private void in1() {
            super.inPackage("in2", "t.fieldIn2", new String[]{"b"}, true, true);
        }

        @Override
        public void commandPackage() {
            this.like0();
            this.like1();
            this.like2();
            this.in0();
            this.in1();
        }
    }
}