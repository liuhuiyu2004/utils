package com.liuhuiyu.demo.domain.demo.service;

import com.liuhuiyu.demo.domain.demo.repository.Demo01Repository;
import com.liuhuiyu.spring.util.SpringApplicationContextUtil;
import org.springframework.stereotype.Service;

/**
 * 功能<p>
 * Created on 2025/7/20 11:07
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@Service
public class Demo01Service {
    public static Demo01Service getInstance() {
        return SpringApplicationContextUtil.getBean(Demo01Service.class);
    }
    private final Demo01Repository repository;

    public Demo01Service(Demo01Repository repository) {
        this.repository = repository;
    }
}
