package com.liuhuiyu.util.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-20 14:53
 */
public class MapUtil {
    /**
     * Map对象转换成Map<String, Object>对象
     *
     * @param obj Map对象
     * @return Map<String, Object>对象
     */
    public static Map<String, Object> mapObjectToStringKeyMap(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            Map<String, Object> resMap = new HashMap<>(map.size());
            map.forEach((k, v) -> resMap.put(k.toString(), v));
            return resMap;
        } else {
            return new HashMap<>(0);
        }
    }

    /**
     * 获取字符串(如果key 不存在返回 “”)
     *
     * @param map map
     * @param key key
     * @return 字符串
     */
    public static String getMapStringValue(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            Object obj = map.get(key);
            return obj == null ? "" : obj.toString();
        } else {
            return "";
        }
    }

    /**
     * 将 map 中的 可转化为 int 的数字转化为 int
     *
     * @param resultMap map
     * @return 修正后的map
     */
    public static Map<String, Object> mapNumberToInt(Map<?, ?> resultMap) {
        Map<String, Object> res = new HashMap<>(resultMap.size());
        for (Object keyObj : resultMap.keySet()) {
            String key = keyObj.toString();
            if (resultMap.get(key) instanceof Double) {
                Double value = (Double) resultMap.get(key);
                if (value.intValue() == value) {
                    res.put(key, ((Double) resultMap.get(key)).intValue());
                } else {
                    res.put(key, resultMap.get(key));
                }
            } else if (resultMap.get(key) instanceof List<?>) {
                res.put(key, listNumberToInt((List<?>) resultMap.get(key)));
            } else if (resultMap.get(key) instanceof Map<?, ?>) {
                res.put(key, mapNumberToInt((Map<?, ?>) resultMap.get(key)));
            } else {
                res.put(key, resultMap.get(key));
            }
        }
        return res;
    }

    /**
     * 将 list 中的 可转化为 int 的数字转化为 int
     *
     * @param list list
     * @return 修正后的list
     */
    public static List<Object> listNumberToInt(List<?> list) {
        List<Object> res = new ArrayList<>(list.size());
        for (Object o : list) {
            if (o instanceof Number) {
                Double value = (Double) o;
                if (value.intValue() == value) {
                    Object v = value.intValue();
                    res.add(v);
                } else {
                    res.add(value);
                }
            } else if (o instanceof Map<?, ?>) {
                res.add(mapNumberToInt((Map<?, ?>) o));
            } else if (o instanceof List<?>) {
                res.add(listNumberToInt((List<?>) o));
            } else {
                res.add(o);
            }
        }
        return res;
    }
}
