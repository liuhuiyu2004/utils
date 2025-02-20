package com.liuhuiyu.core.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-04-07 8:56
 */
public class MethodProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  throws Throwable {
        //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //如果传进来的是一个接口（核心)
        } else {
            return run(method, args);
        }
        return null;
    }

    /**
     * 实现接口的核心方法
     * @param method
     * @param args
     * @return
     */
    public Object run(Method method,Object[] args){
        //TODO
        //如远程http调用
        //如远程方法调用（rmi)
        //....
        return "method call success!";
    }
}
