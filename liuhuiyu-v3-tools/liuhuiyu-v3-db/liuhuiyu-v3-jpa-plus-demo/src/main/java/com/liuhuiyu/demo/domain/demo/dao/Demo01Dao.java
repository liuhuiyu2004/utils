package com.liuhuiyu.demo.domain.demo.dao;

import com.liuhuiyu.demo.db.AbstractDbView;
import com.liuhuiyu.demo.domain.demo.dto.in.Demo01FindDto;
import com.liuhuiyu.demo.domain.demo.dto.out.Demo01Dto;
import com.liuhuiyu.jpa_plus.conditions.SqlConditionWrapper;
import com.liuhuiyu.jpa_plus.conditions.SqlSelectWrapper;
import jakarta.persistence.EntityManager;

/**
 * 功能<p>
 * Created on 2025/7/20 11:10
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class Demo01Dao extends AbstractDbView<Demo01Dto, Demo01FindDto> {
    public Demo01Dao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected void fullWhere(Demo01FindDto whereDto, SqlSelectWrapper selectSql) {

    }

    @Override
    protected String getBaseSql() {
        return Demo01Dto.getBaseSql();
    }

    @Override
    protected String getCountSql() {
        return Demo01Dto.getCountSql();
    }

    @Override
    protected SqlConditionWrapper sqlWhere() {
        return null;
    }
}
