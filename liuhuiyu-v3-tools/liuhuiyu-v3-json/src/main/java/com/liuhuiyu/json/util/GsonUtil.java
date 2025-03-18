package com.liuhuiyu.json.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.liuhuiyu.json.deserializer.ByteArrayDeserializer;

import java.lang.reflect.Type;
import java.util.Map;

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


    public static Map<String, Object> mapOfJsonString(String jsonString) {
        try {
            return GsonUtil.createGson().fromJson(jsonString, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        catch (JsonSyntaxException e) {
            throw new RuntimeException("无法解析成Map格式数据");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Map序列化成指定类<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/17 21:32
     *
     * @param map      map
     * @param classOfT T.class
     * @param <T>      得到的类
     * @return T 得到的类
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> classOfT) {
        String json = GsonUtil.createGson().toJson(map);
        return GsonUtil.createGson().fromJson(json, classOfT);
    }

    /**
     * 将Map序列化成指定类 使用指定的类型适配器 <p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/17 21:28
     *
     * @param map         map
     * @param classOfT    T.class
     * @param typeAdapter typeAdapter
     * @return T 得到的类
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> classOfT, GsonAdapter[] typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (GsonAdapter gsonAdapter : typeAdapter) {
            gsonBuilder.registerTypeAdapter(gsonAdapter.type, gsonAdapter.typeAdapter);
        }
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(map);
        return GsonUtil.createGson().fromJson(json, classOfT);
    }

    public static class GsonAdapter {
        Type type;
        Object typeAdapter;

        public GsonAdapter(Type type, Object typeAdapter) {
            this.type = type;
            this.typeAdapter = typeAdapter;
        }
    }

    public static <T> T cloneObj(T a, Class<T> classOfT) {
        String json = GsonUtil.createGson().toJson(a);
        return GsonUtil.createGson().fromJson(json, classOfT);
    }
}
