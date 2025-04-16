package com.liuhuiyu.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 功能<p>
 * Created on 2025/4/16 20:39
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class App extends SpringBootServletInitializer {
    private static final Logger LOG= LogManager.getLogger(App.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this.getClass());
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        LOG.trace("默认日志输出。");
        LOG.debug("默认日志输出。");
        LOG.info("默认日志输出。");
        LOG.warn("默认日志输出。");
        LOG.error("默认日志输出。");
    }
}