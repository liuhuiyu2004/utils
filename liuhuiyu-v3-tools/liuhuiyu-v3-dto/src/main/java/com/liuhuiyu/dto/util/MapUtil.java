package com.liuhuiyu.dto.util;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/19 11:38
 */
public class MapUtil {
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
