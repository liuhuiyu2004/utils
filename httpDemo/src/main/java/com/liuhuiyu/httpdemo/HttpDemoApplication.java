package com.liuhuiyu.httpdemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动类
 * @author LiuHuiYu
 * Created DateTime 2022-05-10 8:43
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
//@ComponentScan
public class HttpDemoApplication {
    private static final Logger LOG = LogManager.getLogger(HttpDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HttpDemoApplication.class, args);
        LOG.trace("log.trace");
        LOG.debug("log.debug");
        LOG.info("log.info");
        LOG.warn("log.warn");
        LOG.error("log.error");
        LOG.fatal("log.fatal");
    }

}
