package com.liuhuiyu.core.lang.function_interface;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-08-03 9:11
 */
@FunctionalInterface
public interface SupplierException<T> {

    /**
     * 获取函数接口数据
     *
     * @return T 实体
     * @throws Exception 异常信息
     *                   Created DateTime 2023-08-03 9:20
     * @author LiuHuiYu
     */
    T get() throws Exception;
}
