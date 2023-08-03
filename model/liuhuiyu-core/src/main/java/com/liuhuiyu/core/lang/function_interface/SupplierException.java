package com.liuhuiyu.core.lang.function_interface;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-08-03 9:11
 */
@FunctionalInterface
public interface SupplierException<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;
}
