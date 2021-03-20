package com.liuhuiyu.spring_util.run_timer;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-02 11:23
 */
@Log4j2
public class TimerUtil {
    public void runTime(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        long start = System.currentTimeMillis();
        m.invoke(obj);
        long end = System.currentTimeMillis();
        log.info("{}执行时间：{}", m.getName(), (end - start));
    }

    public Object runTime(ProceedingJoinPoint pjp, RunTimer runTimer) throws Throwable {
        Signature sig = pjp.getSignature();
        MethodSignature msig;// = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        long start = System.currentTimeMillis();
        Object res = pjp.proceed();
        long end = System.currentTimeMillis();

        log.info("{}.{} {}执行时间：{}", currentMethod.getDeclaringClass().getName(), currentMethod.getName(), runTimer.explain(), (end - start));
        return res;
    }

    public void getTime() {
        // 获取当前类型名字
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println("current className(expected): " + className);
        try {
            Class<?> c = Class.forName(className);
            Object obj = c.newInstance();
            Method[] methods = c.getDeclaredMethods();
            for (Method m : methods) {
                // 判断该方法是否包含Timer注解
                if (m.isAnnotationPresent(RunTimer.class)) {
                    m.setAccessible(true);
                    long start = System.currentTimeMillis();
                    // 执行该方法
                    m.invoke(obj);
                    long end = System.currentTimeMillis();
                    System.out.println(m.getName() + "() time consumed: " + (end - start) + "\\\\n");
                }
            }
        }
        catch (ReflectiveOperationException e) {
            log.error(e.toString());
        }
    }
}
