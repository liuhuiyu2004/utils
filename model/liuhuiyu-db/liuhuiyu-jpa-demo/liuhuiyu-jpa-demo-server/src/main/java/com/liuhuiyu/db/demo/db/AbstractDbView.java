package com.liuhuiyu.db.demo.db;

import com.liuhuiyu.dto.IComputedValueFilling;
//import com.liuhuiyu.jpa.dm.AbstractSqlViewDm;
import com.liuhuiyu.jpa.oracle.AbstractSqlViewOracle;

import javax.persistence.EntityManager;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2024/11/29 11:45
 */
//public abstract class AbstractDbView<returnT extends IComputedValueFilling, findT> extends AbstractSqlViewDm<returnT, findT> {
public abstract class AbstractDbView<returnT extends IComputedValueFilling, findT> extends AbstractSqlViewOracle<returnT, findT> {
    public AbstractDbView(EntityManager entityManager) {
        super(entityManager);
    }
}
