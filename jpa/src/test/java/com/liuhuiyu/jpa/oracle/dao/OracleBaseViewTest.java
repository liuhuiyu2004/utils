package com.liuhuiyu.jpa.oracle.dao;

import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.jpa.DaoOperator;
import com.liuhuiyu.jpa.WhereFull;
import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;

import javax.sql.DataSource;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 8:56
 */
public class OracleBaseViewTest extends OracleBaseView {

    public OracleBaseViewTest(DataSource dataSource) {
        super(dataSource);
    }

    public PageImpl<String> findPage(DaoOperator<String> b, IPaging t, String sql, WhereFull<IPaging> fullWhere) {
        return new PageImplBuilder<>(b, t, sql, fullWhere)
                .baseWhere(" WHERE(1=1)")
                .order("")
                .buildPage();
    }
}