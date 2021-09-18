package com.liuhuiyu.httpdemo.controller.api;

import com.liuhuiyu.util.model.Result;
import com.liuhuiyu.util.web.AddressRoutingUtil;
import com.liuhuiyu.util.web.HttpUtil;
import com.liuhuiyu.web.controller.AbsBaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-18 15:00
 */
@Controller
@RequestMapping(PostTestController.ROOT)
//@Api(value = "XXXXX模块API")
//@Log4j2
public class PostTestController extends AbsBaseController {
    static final String ROOT = "/api/post";
//region ajax数据

    private static final String PATH_TEST = "/test";

    public static String getTestAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_TEST);
    }

    @ResponseBody
    @RequestMapping(value = PATH_TEST, method = RequestMethod.POST)
    @ApiOperation(value = "说明", notes = "test")
    public Result<?> test() {
        return Result.of("get 无参数测试成功。");
    }
    //endregion
    //region ajax数据

    private static final String PATH_TEST_P = "/test_p";

    public static String getTestPAddress() {
        return AddressRoutingUtil.getFullAddress(HttpUtil.getHttpServletRequest(), ROOT, PATH_TEST_P);
    }

    @ResponseBody
    @RequestMapping(value = PATH_TEST_P, method = RequestMethod.POST)
    public Result<?> testP(String p1) {
        return Result.of("p1=" + p1);
    }
    //endregion
}