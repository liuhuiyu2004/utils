package com.liuhuiyu.httpdemo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 初始化
 * @author LiuHuiYu
 * Created DateTime 2022-05-10 8:43
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HttpDemoApplication.class);
    }

}
