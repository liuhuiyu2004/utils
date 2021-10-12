package com.liuhuiyu.httpdemo.controller.web;


import com.liuhuiyu.web.controller.AbsBaseController;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class HomeControllerController extends AbsBaseController {
    static final String ROOT = "";
    //region 首页地址访问

    @RequestMapping(value = {PATH_INDEX, PATH_DEFAULT})
    public String index() {
        log.trace("log.trace");
        log.debug("log.debug");
        log.info("log.info");
        log.warn("log.warn");
        log.error("log.error");
        log.fatal("log.fatal");
        return "redirect:/swagger-ui/index.html";
    }
    //endregion
}