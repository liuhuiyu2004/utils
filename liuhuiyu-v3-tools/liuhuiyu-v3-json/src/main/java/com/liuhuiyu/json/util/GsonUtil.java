package com.liuhuiyu.json.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liuhuiyu.json.util.deserializer.ByteArrayDeserializer;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/2/14 17:00
 */
public class GsonUtil {
    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(byte[].class, new ByteArrayDeserializer());
        return gsonBuilder.create();
    }
}
