package com.liuhuiyu.core.lang.function_interface;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-08-03 9:08
 */
@FunctionalInterface
public interface RunnableException {
    public abstract void run() throws Exception;
}
