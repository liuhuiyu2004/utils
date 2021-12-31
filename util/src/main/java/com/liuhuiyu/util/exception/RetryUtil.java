package com.liuhuiyu.util.exception;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;

/**
 * 重复执行
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-05 15:44
 */
public class RetryUtil {
    /**
     * 多次重试
     *
     * @param num           重试次数
     * @param callFunctions 重试调用函数
     * @author LiuHuiYu
     * Created DateTime 2021-07-05 16:58
     */
    public static <R> R retry(int num, Callable<R> callFunctions) {
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
    public static <R> R retry(int num, Callable<R> callFunctions, Runnable failedCall) {
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
    public static <R> R retry(Callable<R> callFunctions, Runnable failedCall) {
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
    public static <R> R retry(int num, Callable<R> callable, Runnable... callFunctions) {
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
    public static <R> R retry(int num, boolean loop, Callable<R> callable, Runnable... callFunctions) {
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
}
