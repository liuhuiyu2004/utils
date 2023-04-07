package com.liuhuiyu.core.util.proxy;

import java.lang.reflect.Proxy;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-04-07 8:55
 */
public class Invoker {
    public Object getInstance(Class<?> cls){
        MethodProxy invocationHandler = new MethodProxy();
        Object newProxyInstance = Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[] { cls },
                invocationHandler);
        return (Object)newProxyInstance;
    }
}
