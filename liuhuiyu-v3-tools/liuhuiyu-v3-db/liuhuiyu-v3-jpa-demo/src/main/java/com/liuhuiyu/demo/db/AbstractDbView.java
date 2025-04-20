package com.liuhuiyu.demo.db;

import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.jpa.mysql.view.AbstractSqlViewMySql;
import jakarta.persistence.EntityManager;

/**
 * 功能<p>
 * Created on 2025/4/20 21:13
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public abstract class AbstractDbView <returnT extends IComputedValueFilling, findT>
        extends AbstractSqlViewMySql<returnT, findT> {
    //        extends AbstractSqlViewDm<returnT, findT> {
//        extends AbstractSqlViewKingBase8<returnT, findT> {
    public AbstractDbView(EntityManager entityManager) {
        super(entityManager);
    }
}
