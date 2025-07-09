package com.liuhuiyu.demo.db;

import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.jpa_plus.view.AbstractSqlView;
import jakarta.persistence.EntityManager;

/**
 * 加入视图基本功能<p>
 * Created on 2025/7/9 14:30
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public abstract class AbstractDbView<returnT extends IComputedValueFilling, findT>
        extends AbstractSqlView<returnT, findT> {
    public AbstractDbView(EntityManager entityManager) {
        super(entityManager);
    }
}
