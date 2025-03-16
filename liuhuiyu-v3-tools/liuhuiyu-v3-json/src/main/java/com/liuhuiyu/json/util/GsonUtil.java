package com.liuhuiyu.json.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liuhuiyu.json.util.deserializer.ByteArrayDeserializer;

/**
 * 功能<p>
 * Created on 2025/3/16 21:12
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class GsonUtil {
    /**
     * 创建Gson对象<p>
     * 默认支持byte[]类型转换
     * author liuhuiyu<p>
     * Created DateTime 2025/3/16 21:38
     *
     * @return com.google.gson.Gson
     */
    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(byte[].class, new ByteArrayDeserializer());
        return gsonBuilder.create();
    }

}
