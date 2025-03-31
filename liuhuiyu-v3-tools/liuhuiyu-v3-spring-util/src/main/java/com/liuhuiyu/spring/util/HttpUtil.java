package com.liuhuiyu.spring.util;

import com.liuhuiyu.core.util.Assert;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 功能<p>
 * Created on 2025/3/31 21:16
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class HttpUtil {
    public static @NotNull HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.assertNotNull(attributes, "Spring配置错误");
        return attributes.getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.assertNotNull(attributes, "Spring配置错误");
        return attributes.getResponse();
    }
}
