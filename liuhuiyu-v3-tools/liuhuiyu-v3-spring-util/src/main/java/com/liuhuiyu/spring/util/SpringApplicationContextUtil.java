package com.liuhuiyu.spring.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 功能<p>
 * Created on 2025/3/30 21:01
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@Component
public class SpringApplicationContextUtil implements ApplicationContextAware {
    private static final Logger LOG = LogManager.getLogger(SpringApplicationContextUtil.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        if (SpringApplicationContextUtil.applicationContext == null) {
            SpringApplicationContextUtil.applicationContext = applicationContext;
        }
        LOG.info("===ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext=%1$s==={}", SpringApplicationContextUtil.applicationContext);
        LOG.info("--------------------------------------------------------------");
    }

    /**
     * 获取applicationContext
     * @return org.springframework.context.ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        if( applicationContext==null){
            throw new RuntimeException("applicationContext未初始化");
        }
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     * @param name bean名称
     * @return java.lang.Object
     */
    public static @NotNull Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @param clazz 类.class
     * @return T
     */
    public static <T> @NotNull T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name  名称
     * @param clazz 类class
     * @param <T>   类
     * @return 类实例
     */
    public static <T> @NotNull T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
