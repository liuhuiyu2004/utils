package com.liuhuiyu.db.demo.domain.demo.service;

import com.liuhuiyu.db.demo.domain.demo.dao.DemoTestDao;
import com.liuhuiyu.db.demo.domain.demo.entity.DemoTest;
import com.liuhuiyu.db.demo.domain.demo.repository.DemoTestRepository;
import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.db.demo.domain.demo.in.DemoTestChangeDto;
import com.liuhuiyu.db.demo.domain.demo.in.DemoTestFindDto;
import com.liuhuiyu.db.demo.domain.demo.out.DemoTestDto;
import com.liuhuiyu.spring.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/12/16 9:26
 */
@Service
public class DemoTestService {
    public static DemoTestService getInstance() {
        return SpringUtil.getBean(DemoTestService.class);
    }

    //region 注入
    private DemoTestDao dao;

    @Autowired
    public void setDemoTestDao(DemoTestDao dao) {
        this.dao = dao;
    }

    //endregion
    private final DemoTestRepository repository;

    public DemoTestService(DemoTestRepository repository) {
        this.repository = repository;
    }

    /**
     * 单条记录查询
     *
     * @param dto 查询条件
     * @return java.util.Optional<com.liuhuiyu.contractor_third_party.domain.DemoTest.dto.out.DemoTestDto>
     * @author LiuHuiYu
     * Created DateTime 2024-01-13 17:36
     */
    public Optional<DemoTestDto> findOne(DemoTestFindDto dto) {
        return this.dao.findOne(dto);
    }

    /**
     * 列表查询
     *
     * @param dto 查询条件
     * @return java.util.List<com.liuhuiyu.contractor_third_party.domain.DemoTest.dto.out.DemoTestDto>
     * @author LiuHuiYu
     * Created DateTime 2024-01-13 17:36
     */
    public List<DemoTestDto> findList(DemoTestFindDto dto) {
        return this.dao.findList(dto);
    }

    /**
     * 分页查询
     *
     * @param dto 查询条件
     * @return org.springframework.data.domain.PageImpl<com.liuhuiyu.contractor_third_party.domain.DemoTest.dto.out.DemoTestDto>
     * @author LiuHuiYu
     * Created DateTime 2024-01-13 17:36
     */
    public PageImpl<DemoTestDto> findPage(DemoTestFindDto dto) {
        return this.dao.findPage(dto);
    }


    static Map<String, Function<DemoTestChangeDto, Optional<DemoTest>>> changeMap;

    static {
        changeMap = new HashMap<>(3);
        changeMap.put(DemoTestChangeDto.ADD_MODEL, (v) -> getInstance().add(v));
        changeMap.put(DemoTestChangeDto.UPDATE_MODEL, (v) -> getInstance().update(v));
        changeMap.put(DemoTestChangeDto.DELETE_MODEL, (v) -> getInstance().delete(v));
    }

    /**
     * 更新数据
     *
     * @param changeDto 更新数据
     * @return java.util.Optional<com.liuhuiyu.contractor_third_party.domain.DemoTest.dto.out.DemoTestDto>
     * @author LiuHuiYu
     * Created DateTime 2024-01-13 17:38
     */
    public Optional<DemoTestDto> change(DemoTestChangeDto changeDto) {
        final Function<DemoTestChangeDto, Optional<DemoTest>> changeFunction = changeMap.get(changeDto.getOperating().getModelName());
        Assert.assertNotNull(changeFunction, "未设定更新模式。");
        Optional<DemoTest> optionalPoint = changeFunction.apply(changeDto);
        return optionalPoint.map(v -> this.findOne(DemoTestFindDto.findById(v.getId()))).orElse(Optional.of(changeDto));
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<DemoTest> add(DemoTestChangeDto changeDto) {
        return Optional.empty();
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<DemoTest> update(DemoTestChangeDto changeDto) {
        return Optional.empty();
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<DemoTest> delete(DemoTestChangeDto changeDto) {
        return Optional.empty();
    }
}
