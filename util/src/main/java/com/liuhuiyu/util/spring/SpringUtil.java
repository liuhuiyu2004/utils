//package com.liuhuiyu.util.spring;
//
//import lombok.extern.log4j.Log4j2;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * @author LiuHuiYu
// * @version v1.0.0.0
// * Created DateTime 2020-06-24 14:55
// */
//@Component
//@Log4j2
//public class SpringUtil implements ApplicationContextAware {
//    private static ApplicationContext applicationContext;
//    @Override
//    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
//        if(SpringUtil.applicationContext == null) {
//            SpringUtil.applicationContext = applicationContext;
//        }
//        log.info("--------------------------------------------------------------");
//        log.info("----------------------.util.SpringUtil\"----------------------");
//        log.info("===ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext=%1$s==={}",SpringUtil.applicationContext);
//        log.info("--------------------------------------------------------------");
//    }
//
//    //获取applicationContext
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//
//    //通过name获取 Bean.
//    public static @NotNull Object getBean(String name){
//        return getApplicationContext().getBean(name);
//    }
//
//    //通过class获取Bean.
//    public static <T> @NotNull T getBean(Class<T> clazz){
//        return getApplicationContext().getBean(clazz);
//    }
//
//    /**
//     * 通过name,以及Clazz返回指定的Bean
//     * @param name  名称
//     * @param clazz 类class
//     * @param <T> 类
//     * @return 类实例
//     */
//    public static <T> @NotNull T getBean(String name, Class<T> clazz){
//        return getApplicationContext().getBean(name, clazz);
//    }
//}
