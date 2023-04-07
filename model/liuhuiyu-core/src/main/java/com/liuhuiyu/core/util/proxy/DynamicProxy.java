package com.liuhuiyu.core.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-04-07 8:40
 */
public class DynamicProxy implements InvocationHandler {
    private Object target;

    public DynamicProxy(Object target, Runnable beforeMethodInvocation, Runnable afterMethodInvocation) {
        this.target = target;
        this.afterMethodInvocation = afterMethodInvocation;
        this.beforeMethodInvocation = beforeMethodInvocation;
    }

    Runnable beforeMethodInvocation;
    Runnable afterMethodInvocation;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (beforeMethodInvocation != null) {
            beforeMethodInvocation.run();
        }
        Object result = method.invoke(target, args);
        if (afterMethodInvocation != null) {
            afterMethodInvocation.run();
        }
        return result;
    }

    public static <T> T proxy(T v) {
        return proxy(v, () -> {
        }, () -> {
        });
    }

    public static <T> T proxy(T v, Runnable beforeMethodInvocation, Runnable afterMethodInvocation) {
        DynamicProxy dynamicProxy = new DynamicProxy(v, beforeMethodInvocation, afterMethodInvocation);
        ClassLoader classLoader = v.getClass().getClassLoader();
        //获取类实现的接口
        Class<?>[] interfaces = v.getClass().getInterfaces();
        //创建代理对象
        return (T) Proxy.newProxyInstance(classLoader, interfaces, dynamicProxy);
    }
    public Object getInstance(Class<?> cls){
        MethodProxy invocationHandler = new MethodProxy();
        Object newProxyInstance = Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[] { cls },
                invocationHandler);
        return (Object)newProxyInstance;
    }
}
