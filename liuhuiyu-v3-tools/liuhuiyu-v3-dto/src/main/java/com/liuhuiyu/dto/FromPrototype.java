package com.liuhuiyu.dto;

import com.google.gson.Gson;

/**
 * 从原型创建（可以作为 深层Clone使用）<p>
 * Created on 2025/3/19 21:04
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface FromPrototype extends ISerializationJson {

    /**
     * 将当前信息转换为指定类型的信息（相同属性数据深层复制）<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/19 21:08
     *
     * @param classOfT      转换后的类
     * @param fromPrototype 可转换为json字符串的类型
     * @param <T>           类
     * @return T 相同属性转换后新类
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

    /**
     * 将当前信息转换为指定类型的信息（相同属性数据深层复制）<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/19 21:09
     *
     * @param classOfT 类型
     * @param obj      原始对象
     * @param <T>      类型
     * @return T
     */
    static <T> T fromObj(Class<T> classOfT, Object obj) {
        if (classOfT == null) {
            throw new RuntimeException("类型不能为null");
        }
        if (obj == null) {
            throw new RuntimeException("要转换的数据不能为null");
        }
        return new Gson().fromJson(new Gson().toJson(obj), classOfT);
    }

    /**
     * 将当前信息转换为指定类型的信息（相同属性数据深层复制）<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/19 21:05
     *
     * @param classOfT 转换后类
     * @param <T>      转换后的类型
     * @return T
     */
    default <T> T fromPrototype(Class<T> classOfT) {
        return FromPrototype.fromPrototype(classOfT, this);
    }
}
