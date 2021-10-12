package com.liuhuiyu.httpdemo.controller.api;

import com.liuhuiyu.util.model.Result;
import com.liuhuiyu.util.web.AddressRoutingUtil;
import com.liuhuiyu.util.web.HttpUtil;
import com.liuhuiyu.web.controller.AbsBaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-18 11:47
 */
@Controller
@RequestMapping(GetTestController.ROOT)
//@Api(value = "XXXXX模块API")
@Log4j2
public class GetTestController extends AbsBaseController {
    static final String ROOT = "/api/get";

    //region ajax数据

    private static final String PATH_TEST = "/test";

    public static String getTestAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_TEST);
    }

    @ResponseBody
    @RequestMapping(value = PATH_TEST, method = RequestMethod.GET)
    @ApiOperation(value = "说明", notes = "")
    public Result<?> test() {
        log.trace("log.trace");
        log.debug("log.debug");
        log.info("log.info");
        log.warn("log.warn");
        log.error("log.error");
        log.fatal("log.fatal");
        return Result.of("get 无参数测试成功。");
    }
    //endregion
    //region ajax数据

    private static final String PATH_TEST_P = "/test_p";

    public static String getTestPAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_TEST_P);
    }

    @ResponseBody
    @RequestMapping(value = PATH_TEST_P, method = RequestMethod.GET)
    public Result<?> testP(String p1) {
        return Result.of("p1=" + p1);
    }
    //endregion
}