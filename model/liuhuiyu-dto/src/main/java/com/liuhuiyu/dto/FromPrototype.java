package com.liuhuiyu.dto;

import com.google.gson.Gson;

/**
 * 从原型创建（可以作为 深层Clone使用）
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 10:08
 */
public interface FromPrototype {
    /**
     * 将当前信息转换为指定类型的信息（相同属性数据深层复制）
     *
     * @param classOfT 转换后类
     * @param <T>      转换后的类型
     * @return T
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:48
     */
    <T> T fromPrototype(Class<T> classOfT);

    /**
     * 将当前信息转换为指定类型的信息（相同属性数据深层复制）
     *
     * @param classOfT      转换后的类
     * @param fromPrototype 可转换为json字符串的类型
     * @param <T>           类
     * @return T 相同属性转换后新类
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:50
     */
    static <T> T fromPrototype(Class<T> classOfT, ISerializationJson fromPrototype) {
        if (classOfT == null) {
            throw new RuntimeException("类型不能为null");
        }
        if (fromPrototype == null) {
            throw new RuntimeException("要转换的数据不能为null");
        }
        return new Gson().fromJson(fromPrototype.toJson(), classOfT);
    }
}
