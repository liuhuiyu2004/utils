package com.liuhuiyu.util.exception;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * 重复执行
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-05 15:44
 */
@Log4j2
public class RetryUtil {
    /**
     * 多次重试
     *
     * @param num           重试次数
     * @param callFunctions 重试调用函数
     * @author LiuHuiYu
     * Created DateTime 2021-07-05 16:58
     */
    public static <T> T retry(int num, Callable<T> callFunctions) {
        return retry(num, false, callFunctions);
    }

    /**
     * 多次重试
     *
     * @param num           执行次数(失败后重试)
     * @param callFunctions 重试调用函数
     * @param failedCall    调用函数失败后执行
     * @author LiuHuiYu
     * Created DateTime 2021-07-05 16:58
     */
    public static <T> T retry(int num, Callable<T> callFunctions, Runnable failedCall) {
        return retry(num, true, callFunctions, failedCall);
    }

    /**
     * 重试一次
     *
     * @param callFunctions 重试调用函数
     * @param failedCall    调用函数失败后执行
     * @author LiuHuiYu
     * Created DateTime 2021-07-05 16:58
     */
    public static <T> T retry(Callable<T> callFunctions, Runnable failedCall) {
        return retry(2, true, callFunctions, failedCall);
    }

    /**
     * 失败重试
     *
     * @param num           重试次数
     * @param callFunctions [0]重拾函数，>0 失败后按照失败次数顺序执行 （失败1 执行【1】）
     * @author LiuHuiYu
     * Created DateTime 2021-07-05 17:13
     */
    public static <T> T retry(int num, Callable<T> callable, Runnable... callFunctions) {
        return retry(num, true, callable, callFunctions);
    }

    /**
     * 失败重试
     *
     * @param num           重试次数
     * @param callFunctions [0]重拾函数，>0 失败后按照失败次数顺序执行 （失败1 执行【1】）
     * @author LiuHuiYu
     * Created DateTime 2021-07-05 17:13
     */
    public static <T> T retry(int num, boolean loop, Callable<T> callable, Runnable... callFunctions) {
        int pointer = -1;
        try {
            for (int i = 0; i < num - 1; i++) {
                try {
                    return callable.call();
                }
                catch (Exception e) {
                    if (pointer > callFunctions.length) {
                        if (loop) {
                            pointer = 0;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        pointer++;
                    }
                    callFunctions[pointer].run();
                }
            }
            return callable.call();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//
//    private static ThreadLocal<Integer> retryTimesInThread = new ThreadLocal<>();
//
//    /**
//     * 设置当前方法重试次数
//     *
//     * @param retryTimes
//     * @return
//     */
//    public static RetryUtil setRetryTimes(Integer retryTimes) {
//        if (retryTimesInThread.get() == null) {
//            retryTimesInThread.set(retryTimes);
//        }
//        return new RetryUtil();
//    }
//
//    /**
//     * 重试当前方法
//     * <p>按顺序传入调用者方法的所有参数</p>
//     *
//     * @param args
//     * @return
//     */
//    public Object retry(Object... args) {
//        try {
//            Integer retryTimes = retryTimesInThread.get();
//            if (retryTimes <= 0) {
//                retryTimesInThread.remove();
//                return null;
//            }
//            retryTimesInThread.set(--retryTimes);
//            String upperClassName = Thread.currentThread().getStackTrace()[2].getClassName();
//            String upperMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
//
//            Class clazz = Class.forName(upperClassName);
//            Object targetObject = clazz.newInstance();
//            Method targetMethod = null;
//            for (Method method : clazz.getDeclaredMethods()) {
//                if (method.getName().equals(upperMethodName)) {
//                    targetMethod = method;
//                    break;
//                }
//            }
//            if (targetMethod == null) {
//                return null;
//            }
//            targetMethod.setAccessible(true);
//            return targetMethod.invoke(targetObject, args);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
