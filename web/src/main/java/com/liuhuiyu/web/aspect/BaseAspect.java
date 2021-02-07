package com.liuhuiyu.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 基础拦截器
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2018-06-28 16:49
 */
public abstract class BaseAspect {


    protected Boolean isResponseBody (ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(ResponseBody.class)!=null;
    }

    /**
     * 获取 HttpServletRequest
     * @return HttpServletRequest
     */
    protected HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException("ServletRequestAttributes 空指针异常。");
        }
        return attributes.getRequest();
    }

    /**
     * 获取 HttpServletResponse
     * @return HttpServletResponse
     */
    protected HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException("ServletRequestAttributes 空指针异常。");
        }
        return attributes.getResponse();
    }
}