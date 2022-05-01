package com.liuhuiyu.activiti_editor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-11-23 9:11
 */
@SpringBootConfiguration
//@EnableOpenApi
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class,
                ManagementWebSecurityAutoConfiguration.class
        }
)
public class ActivitiEditorApp extends SpringBootServletInitializer {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ActivitiEditorApp.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this.getClass());
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivitiEditorApp.class, args);
        LOG.trace("默认日志输出。");
        LOG.debug("默认日志输出。");
        LOG.info("默认日志输出。");
        LOG.warn("默认日志输出。");
        LOG.error("默认日志输出。");
    }
}
