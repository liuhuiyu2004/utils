package com.liuhuiyu.jpa_plus.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 功能<p>
 * Created on 2025/7/17 11:17
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class CustomUUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return UUID.randomUUID().toString().replace("-", "");
    }
}