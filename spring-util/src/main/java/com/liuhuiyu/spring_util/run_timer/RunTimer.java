package com.liuhuiyu.spring_util.run_timer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 运行时间注解
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-02 11:22
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RunTimer {
    String explain() default "";
}
