package com.liuhuiyu.spring.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 在初始化的时候加入  @Bean public SpringUtil springUtilBean(){  return new SpringUtil();  }
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-06-24 14:55
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static final Logger LOG = LogManager.getLogger(SpringUtil.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        LOG.info("===ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext=%1$s==={}", SpringUtil.applicationContext);
        LOG.info("--------------------------------------------------------------");
    }

    /**
     * 获取applicationContext
     * Created DateTime 2021-12-15 9:26
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
     * Created DateTime 2021-12-15 9:26
     * @param name bean名称
     * @return java.lang.Object
     */
    public static @NotNull Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * Created DateTime 2021-12-15 9:27
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
