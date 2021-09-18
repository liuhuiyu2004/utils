package com.liuhuiyu.httpdemo.controller.web;


import com.liuhuiyu.web.controller.AbsBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-05-12 15:11
 */
@Controller
@ApiIgnore
@RequestMapping(HomeControllerController.ROOT)
public class HomeControllerController extends AbsBaseController {
    static final String ROOT = "";
    //region 首页地址访问

    @RequestMapping(value = {PATH_INDEX, PATH_DEFAULT})
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }
    //endregion
}