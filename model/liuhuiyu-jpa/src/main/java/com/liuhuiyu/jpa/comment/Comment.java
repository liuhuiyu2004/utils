package com.liuhuiyu.jpa.comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库备注信息
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-11-19 11:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Comment {
    String value() default "";
}