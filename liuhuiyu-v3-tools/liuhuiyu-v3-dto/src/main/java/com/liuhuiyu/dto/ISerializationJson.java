package com.liuhuiyu.dto;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 将当前类序列化成json字符串<p>
 * Created on 2025/3/19 21:06
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface ISerializationJson extends Serializable {
    /**
     * 将当前类序列化成json字符串<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/19 21:06
     *
     * @return java.lang.String
     */
    default String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * 将对象转换成json字符串<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/19 21:07
     *
     * @param object 转换对象
     * @return java.lang.String
     */
    static String getJson(Object object) {
        return new Gson().toJson(object);
    }
}
