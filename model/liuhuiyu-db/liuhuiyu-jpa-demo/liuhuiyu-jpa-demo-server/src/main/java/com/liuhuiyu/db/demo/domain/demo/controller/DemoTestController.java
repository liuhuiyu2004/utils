package com.liuhuiyu.db.demo.domain.demo.controller;

import com.liuhuiyu.db.demo.domain.demo.service.DemoTestService;
import com.liuhuiyu.db.demo.constant.WebAddressConstant;
import com.liuhuiyu.db.demo.domain.demo.in.DemoTestChangeDto;
import com.liuhuiyu.db.demo.domain.demo.in.DemoTestFindDto;
import com.liuhuiyu.db.demo.domain.demo.out.DemoTestDto;
import com.liuhuiyu.dto.Result;
import com.liuhuiyu.spring.web.AddressRoutingUtil;
import com.liuhuiyu.spring.web.HttpUtil;
import com.liuhuiyu.web.controller.AbsBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/12/16 9:26
 */
@RestController
@RequestMapping(DemoTestController.ROOT)
@Api(value = "DemoTestAPI", tags = "DemoTest模块API")
public class DemoTestController extends AbsBaseController {
    static final String ROOT = WebAddressConstant.DEMO_TEST_ADDRESS_ROOT;
    //region 注入
    private DemoTestService service;

    @Autowired
    public void setDemoTestService(DemoTestService service) {
        this.service = service;
    }
    //endregion

    //region 单条数据查询 ajax数据

    private static final String PATH_FIND_ONE = "/find_one";

    public static String getFindOneAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_FIND_ONE);
    }

    @PostMapping(value = PATH_FIND_ONE)
    @ApiOperation(value = "单条数据查询", notes = "单条数据查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "dto", value = "单条数据查询", dataTypeClass = DemoTestFindDto.class, paramType = "body", required = true),
    })
    public Result<Optional<DemoTestDto>> findOne(@Validated @RequestBody DemoTestFindDto dto) {
        final Optional<DemoTestDto> one = this.service.findOne(dto);
        return Result.of(one);
    }

    //endregion

    //region 列表查询 ajax数据

    private static final String PATH_FIND_LIST = "/find_list";

    public static String getFindListAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_FIND_LIST);
    }

    @PostMapping(value = PATH_FIND_LIST)
    @ApiOperation(value = "列表查询", notes = "列表查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "dto", value = "列表查询", dataTypeClass = DemoTestFindDto.class, paramType = "body", required = true),
    })
    public Result<List<DemoTestDto>> findList(@Validated @RequestBody DemoTestFindDto dto) {
        final List<DemoTestDto> list = this.service.findList(dto);
        return Result.of(list);
    }
    //endregion

    //region 分页查询 ajax数据

    private static final String PATH_FIND_PAGE = "/find_page";

    public static String getFindPageAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_FIND_PAGE);
    }

    @PostMapping(value = PATH_FIND_PAGE)
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "dto", value = "分页查询", dataTypeClass = DemoTestFindDto.class, paramType = "body", required = true),
    })
    public Result<PageImpl<DemoTestDto>> findPage(@Validated @RequestBody DemoTestFindDto dto) {
        final PageImpl<DemoTestDto> page = this.service.findPage(dto);
        return Result.of(page);
    }
    //endregion

    //region 更新数据 ajax数据

    private static final String PATH_CHANGE = "/change";

    public static String getChangeAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_CHANGE);
    }

    @PostMapping(value = PATH_CHANGE)
    @ApiOperation(value = "更新数据", notes = "更新数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "dto", value = "更新数据", dataTypeClass = DemoTestChangeDto.class, paramType = "body", required = true),
    })
    public Result<Optional<DemoTestDto>> change(@Validated @RequestBody DemoTestChangeDto dto) {
        final Optional<DemoTestDto> change = this.service.change(dto);
        return Result.of(change);
    }
    //endregion
}