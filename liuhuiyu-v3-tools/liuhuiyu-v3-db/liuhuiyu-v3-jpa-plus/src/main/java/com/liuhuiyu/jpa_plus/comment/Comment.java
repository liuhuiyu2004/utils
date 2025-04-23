package com.liuhuiyu.jpa_plus.comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能<p>
 * Created on 2025/4/23 20:41
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Comment {
    String value() default "";
}
