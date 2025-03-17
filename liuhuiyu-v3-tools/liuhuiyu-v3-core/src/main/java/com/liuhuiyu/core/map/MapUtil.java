package com.liuhuiyu.core.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map json转换<p>
 * Created on 2025/3/17 21:35
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class MapUtil {
    //region 静态方法

    public static Map<String, Object> mapObjectToStringKeyMap(Object obj) {
        if (obj == null) {
            return null;
        }
        else if (obj instanceof Map<?, ?> map) {
            Map<String, Object> resMap = new HashMap<>(map.size());
            map.forEach((k, v) -> resMap.put(k.toString(), v));
            return resMap;
        }
        else {
            return new HashMap<>(0);
        }
    }

    public static String getMapStringValue(Map<String, Object> map, String key) {
        return getMapStringValue(map, key, "");
    }

    public static String getMapStringValue(Map<String, Object> map, String key, String defValue) {
        if (map.containsKey(key)) {
            Object obj = map.get(key);
            return obj == null ? defValue : obj.toString();
        }
        else {
            return defValue;
        }
    }

    public static Integer getMapIntegerValue(Map<String, Object> map, String key) {
        return getMapIntegerValue(map, key, 0);
    }

    public static Integer getMapIntegerValue(Map<String, Object> map, String key, Integer defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        else {
            String value = obj.toString();
            try {
                return Integer.parseInt(value);
            }
            catch (NumberFormatException ex) {
                return defValue;
            }
        }
    }

    public static Float getMapFloatValue(Map<String, Object> map, String key) {
        return getMapFloatValue(map, key, 0F);
    }

    public static Float getMapFloatValue(Map<String, Object> map, String key, Float defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        else {
            String value = obj.toString();
            try {
                return Float.parseFloat(value);
            }
            catch (NumberFormatException ex) {
                return defValue;
            }
        }
    }

    public static Long getMapLongValue(Map<String, Object> map, String key) {
        return getMapLongValue(map, key, 0L);
    }

    public static Long getMapLongValue(Map<String, Object> map, String key, Long defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        else {
            String value = obj.toString();
            try {
                return Long.parseLong(value);
            }
            catch (NumberFormatException ex) {
                return defValue;
            }
        }
    }

    public static Boolean getMapBooleanValue(Map<String, Object> map, String key) {
        return getMapBooleanValue(map, key, false);
    }

    public static Boolean getMapBooleanValue(Map<String, Object> map, String key, boolean defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        try {
            return Boolean.parseBoolean(obj.toString());
        }
        catch (Exception ex) {
            return defValue;
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
            if (resultMap.get(key) instanceof Double value) {
                if (value.intValue() == value) {
                    res.put(key, ((Double) resultMap.get(key)).intValue());
                }
                else {
                    res.put(key, resultMap.get(key));
                }
            }
            else if (resultMap.get(key) instanceof List<?>) {
                res.put(key, listNumberToInt((List<?>) resultMap.get(key)));
            }
            else if (resultMap.get(key) instanceof Map<?, ?>) {
                res.put(key, mapNumberToInt((Map<?, ?>) resultMap.get(key)));
            }
            else {
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
            switch (o) {
                case Number ignored -> {
                    Double value = (Double) o;
                    if (value.intValue() == value) {
                        Object v = value.intValue();
                        res.add(v);
                    }
                    else {
                        res.add(value);
                    }
                }
                case Map<?, ?> map -> res.add(mapNumberToInt(map));
                case List<?> objects -> res.add(listNumberToInt(objects));
                case null, default -> res.add(o);
            }
        }
        return res;
    }


    //endregion

    public static Map<String, Object> mapDoubleToInt(Map<?, ?> resultMap) {
        Map<String, Object> res = new HashMap<>(resultMap.size());
        for (Object keyObj : resultMap.keySet()) {
            String key = keyObj.toString();
            if (resultMap.get(key) instanceof Double value) {
                if (value.intValue() == value) {
                    res.put(key, ((Double) resultMap.get(key)).intValue());
                }
                else {
                    res.put(key, resultMap.get(key));
                }
            }
            else if (resultMap.get(key) instanceof List<?>) {
                res.put(key, listDoubleToInt((List<?>) resultMap.get(key)));
            }
            else if (resultMap.get(key) instanceof Map<?, ?>) {
                res.put(key, mapDoubleToInt((Map<?, ?>) resultMap.get(key)));
            }
            else {
                res.put(key, resultMap.get(key));
            }
        }
        return res;
    }

    public static List<Object> listDoubleToInt(List<?> list) {
        List<Object> res = new ArrayList<>(list.size());
        for (Object o : list) {
            switch (o) {
                case Number ignored -> {
                    Double value = (Double) o;
                    if (value.intValue() == value) {
                        Object v = value.intValue();
                        res.add(v);
                    }
                    else {
                        res.add(value);
                    }
                }
                case Map<?, ?> map -> res.add(mapDoubleToInt(map));
                case List<?> objects -> res.add(listDoubleToInt(objects));
                case null, default -> res.add(o);
            }
        }
        return res;
    }
}
