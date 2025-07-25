package com.liuhuiyu.demo.domain.demo.service;

import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.demo.domain.demo.dao.Demo01Dao;
import com.liuhuiyu.demo.domain.demo.dto.in.Demo01ChangeDto;
import com.liuhuiyu.demo.domain.demo.dto.in.Demo01FindDto;
import com.liuhuiyu.demo.domain.demo.dto.out.Demo01Dto;
import com.liuhuiyu.demo.domain.demo.entity.Demo01;
import com.liuhuiyu.demo.domain.demo.repository.Demo01Repository;
import com.liuhuiyu.spring.util.SpringApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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

    private Demo01Dao dao;

    @Autowired
    private void setDao(Demo01Dao dao) {
        this.dao = dao;
    }

    private final Demo01Repository repository;

    public Demo01Service(Demo01Repository repository) {
        this.repository = repository;
    }

    public Optional<Demo01Dto> findOne(Demo01FindDto findDto) {
        return this.dao.findOne(findDto);
    }

    public List<Demo01Dto> findList(Demo01FindDto findDto) {
        return this.dao.findList(findDto);
    }

    public PageImpl<Demo01Dto> findPage(Demo01FindDto findDto) {
        return this.dao.findPage(findDto);
    }

    //region 更新代码
    static Map<String, Function<Demo01ChangeDto, Optional<Demo01>>> changeMap;

    static {
        changeMap = new HashMap<>(3);
        changeMap.put(Demo01ChangeDto.ADD_MODEL, (v) -> getInstance().add(v));
        changeMap.put(Demo01ChangeDto.UPDATE_MODEL, (v) -> getInstance().update(v));
        changeMap.put(Demo01ChangeDto.DELETE_MODEL, (v) -> getInstance().delete(v));
    }

    public Optional<Demo01Dto> change(Demo01ChangeDto changeDto) {
        final Function<Demo01ChangeDto, Optional<Demo01>> changeFunction = changeMap.get(changeDto.getOperating().getModelName());
        Assert.assertNotNull(changeFunction, "未设定更新模式。");
        Optional<Demo01> optionalPoint = changeFunction.apply(changeDto);
        return optionalPoint.map(v -> this.findOne(Demo01FindDto.findById(v.getId()))).orElse(Optional.of(changeDto));
    }

    private Optional<Demo01> add(Demo01ChangeDto changeDto) {
        final Demo01 data = new Demo01();
        //填充更新数据
        data.createOperate(changeDto);
        this.repository.save(data);
        return Optional.empty();
    }

    private Optional<Demo01> update(Demo01ChangeDto changeDto) {
        final Demo01 data = this.repository.findById(changeDto.getId()).orElseThrow(() -> new RuntimeException("未找到数据"));
        //填充更新数据
        data.createOperate(changeDto);
        this.repository.save(data);
        return Optional.of(data);
    }

    private Optional<Demo01> delete(Demo01ChangeDto changeDto) {
        final Demo01 data = this.repository.findById(changeDto.getId()).orElseThrow(() -> new RuntimeException("未找到数据"));
        //填充更新数据
        data.deleteOperate(changeDto);
        this.repository.save(data);
        return Optional.empty();
    }
    //endregion
}
