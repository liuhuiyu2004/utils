package com.liuhuiyu.demo.controller;

import com.liuhuiyu.demo.constant.WebAddressConstant;
import com.liuhuiyu.web.controller.AbsBaseController;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能<p>
 * Created on 2025/4/18 20:59
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@Controller
@RequestMapping(HomeController.ROOT)
@Tag(name = "首页工具", description = "首页工具")
public class HomeController  extends AbsBaseController {
    static final String ROOT = WebAddressConstant.HOME_ADDRESS_ROOT;

    //region 首页地址访问
//    @Parameter(hidden = true)
    @Hidden
    @RequestMapping(value = {PATH_INDEX, PATH_DEFAULT})
    public String index() {
        return "redirect:" + WebAddressConstant.SWAGGER_UI_URL;
    }
    //endregion
}
