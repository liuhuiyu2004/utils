package com.liuhuiyu.util.web;

import com.liuhuiyu.util.asserts.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-06-23 13:23
 */
public class HttpUtil {
    public static @NotNull HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        LhyAssert.assertNotNull(attributes, "Spring配置错误");
        return attributes.getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        LhyAssert.assertNotNull(attributes, "Spring配置错误");
        return attributes.getResponse();
    }
}

