package com.liuhuiyu.demo.domain.demo.controller;

import com.liuhuiyu.demo.constant.WebAddressConstant;
import com.liuhuiyu.demo.domain.demo.dto.in.Demo01ChangeDto;
import com.liuhuiyu.demo.domain.demo.dto.in.Demo01FindDto;
import com.liuhuiyu.demo.domain.demo.dto.out.Demo01Dto;
import com.liuhuiyu.demo.domain.demo.service.Demo01Service;
import com.liuhuiyu.spring.util.HttpUtil;
import com.liuhuiyu.web.controller.AbsBaseController;
import com.liuhuiyu.web.help.Result;
import com.liuhuiyu.web.util.AddressRoutingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 功能<p>
 * Created on 2025/7/29 19:34
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@RestController
@RequestMapping(DemoController.ROOT)
public class DemoController extends AbsBaseController {

    static final String ROOT = WebAddressConstant.DEMO_TEST_ADDRESS_ROOT;


    //region 注入
    private Demo01Service service;

    @Autowired
    public void setCarInfoHistoryService(Demo01Service service) {
        this.service = service;
    }
    //endregion

    //region 车辆在厂分页查询 ajax数据

    private static final String PATH_FIND_PAGE = "/find_page";

    public static String getFindPageAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_FIND_PAGE);
    }

    @PostMapping(value = PATH_FIND_PAGE)
    public Result<PageImpl<Demo01Dto>> findPage(@Validated @RequestBody Demo01FindDto findDto) {
        PageImpl<Demo01Dto> page = this.service.findPage(findDto);
        return Result.of(page);
    }
    //endregion

    //region 根据id查询车辆在厂详情 ajax数据

    private static final String PATH_FIND_ONE = "/find_one";

    public static String getFindByIdAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_FIND_ONE);
    }

    @PostMapping(value = PATH_FIND_ONE)
    public Result<Optional<Demo01Dto>> findOne(@Validated @RequestBody Demo01FindDto findDto) {
        Optional<Demo01Dto> carInfoHistoryDto = this.service.findOne(findDto);
        return Result.of(carInfoHistoryDto);
    }
    //endregion

    //region 车辆在厂信息变更 ajax数据

    private static final String PATH_CHANGE = "/change";

    public static String getChangeAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_CHANGE);
    }

    @PostMapping(value = PATH_CHANGE)
    public Result<Optional<Demo01Dto>> change(@Validated @RequestBody Demo01ChangeDto changeDto) {
        Optional<Demo01Dto> carInfoHistoryDto = this.service.change(changeDto);
        return Result.of(carInfoHistoryDto);
    }
    //endregion

}
