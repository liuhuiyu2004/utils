package com.liuhuiyu.dto;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 将当前类序列化成json字符串
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 10:15
 */
public interface ISerializationJson extends Serializable {
    /**
     * 将当前类序列化成json字符串
     *
     * @return java.lang.String

     * Created DateTime 2022-01-20 15:30
     */
    default String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * 将对象转换成json字符串
     *
     * @param object 转换对象
     * @return java.lang.String

     * Created DateTime 2023-10-19 11:27
     */
    static String getJson(Object object) {
        return new Gson().toJson(object);
    }
}