package com.liuhuiyu.core.map;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Map工具<p>
 * Created on 2025/3/16 21:11
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class MapHelp {

    boolean throwException = false;

    public boolean isThrowException() {
        return throwException;
    }

    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }

    private final Map<String, Object> map;

    public MapHelp(Map<String, Object> map) {
        this.map = map;
    }

    public String getStringValue(String key) {
        return MapUtil.getMapStringValue(this.map, key);
    }

    public String getStringValue(String key, String defValue) {
        return MapUtil.getMapStringValue(this.map, key, defValue);
    }

    public Integer getIntegerValue(String key) {
        return MapUtil.getMapIntegerValue(map, key);
    }

    public Integer getIntegerValue(String key, Integer defValue) {
        return MapUtil.getMapIntegerValue(map, key, defValue);
    }

    public Float getFloatValue(String key) {
        return MapUtil.getMapFloatValue(map, key);
    }

    public Float getFloatValue(String key, Float defValue) {
        return MapUtil.getMapFloatValue(map, key, defValue);
    }

    public Long getLongValue(String key) {
        return MapUtil.getMapLongValue(map, key);
    }

    public Long getLongValue(String key, Long defValue) {
        return MapUtil.getMapLongValue(map, key, defValue);
    }

    public Object getObjectValue(String key) {
        return this.getMapObjectValue(map, key);
    }

    private Object getMapObjectValue(Map<String, Object> map, String key) {
        return getMapObjectValue(map, key, null);
    }

    public Object getMapObjectValue(Map<String, Object> map, String key, Object defValue) {
        return map.getOrDefault(key, defValue);
    }

    public Object getObjectValue(String key, Object defValue) {
        return this.getMapObjectValue(map, key, defValue);
    }

    public <T> T getValue(String key, T defValue, Class<T> clazz) {
        Object obj = map.getOrDefault(key, defValue);
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        else if (throwException) {
            throw new RuntimeException("类型转换失败。");
        }
        else {
            return defValue;
        }
    }

    public Boolean getBooleanValue(String key) {
        return MapUtil.getMapBooleanValue(map, key);
    }

    public Boolean getBooleanValue(String key, boolean defValue) {
        return MapUtil.getMapBooleanValue(map, key, defValue);
    }

    public Timestamp getTimestampValue(String key) {
        return getTimestampValue(key, Timestamp.valueOf(LocalDateTime.now()));
    }

    public Timestamp getTimestampValue(String key, Timestamp defValue) {
        Object obj = map.get(key);
        switch (obj) {
            case Timestamp timestamp -> {
                return timestamp;
            }
            case Number number -> {
                return new Timestamp(number.longValue());
            }
            case String s -> {
                try {
                    return Timestamp.valueOf((String) obj);
                }
                catch (Exception ex) {
                    return defValue;
                }
            }
            case null, default -> {
                return defValue;
            }
        }
    }

    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, BigDecimal.ZERO);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        Object obj = map.get(key);
        switch (obj) {
            case Double v -> {
                return BigDecimal.valueOf(v);
            }
            case Long l -> {
                return BigDecimal.valueOf(l);
            }
            case Number number -> {
                return BigDecimal.valueOf(number.doubleValue());
            }
            case String s -> {
                try {
                    return new BigDecimal((String) obj);
                }
                catch (Exception ex) {
                    return defValue;
                }
            }
            case null, default -> {
                return defValue;
            }
        }
    }

    public <T> T getValue(String key, Function<Object, T> function) {
        return function.apply(map.getOrDefault(key, null));
    }

    public <T> List<T> getListValue(String key, Function<Object, T> function) {
        Function<Object, ResInfo<T>> function2 = (obj) -> new ResInfo<>(true, function.apply(obj));
        return getCollectionValueAllowJudgment(key, function2, () -> new ArrayList<>(0));
    }

    public <T> List<T> getListMapValue(String key, Function<Map<String, Object>, T> function) {
        Function<Object, ResInfo<T>> function2 = (obj) -> {
            Map<String, Object> map = MapUtil.mapObjectToStringKeyMap(obj);
            return new ResInfo<>(true, function.apply(map));
        };
        return getCollectionValueAllowJudgment(key, function2, () -> new ArrayList<>(0));
    }

    /**
     * Collection获取(List;Set)
     *
     * @param key                  键值
     * @param function             转换
     * @param initializeCollection 初始化 Collection
     * @param <T>                  T
     * @param <R>                  R
     * @return R  集合
     * <p>
     * Created DateTime 2025/3/17 22:00
     */
    public <T, R extends Collection<T>> R getCollectionValue(String key, Function<Object, T> function, Supplier<R> initializeCollection) {
        Function<Object, ResInfo<T>> function2 = (obj) -> new ResInfo<>(true, function.apply(obj));
        return getCollectionValueAllowJudgment(key, function2, initializeCollection);
    }

    /**
     * Collection获取(List;Set) <p>
     * Created DateTime 2025/3/17 21:59
     *
     * @param key                  键值
     * @param function             转换
     * @param initializeCollection 初始化 Collection
     * @param <R>                  R
     * @param <T>                  T
     * @return R  集合对象
     */
    public <T, R extends Collection<T>> R getCollectionValueAllowJudgment(String key, Function<Object, ResInfo<T>> function, Supplier<R> initializeCollection) {
        R resList = initializeCollection.get();
        if (this.map.containsKey(key)) {
            Object obj = this.map.get(key);
            if (obj instanceof Collection<?> list) {
                list.forEach(item -> {
                    ResInfo<T> resInfo = function.apply(item);
                    if (resInfo.res) {
                        resList.add(resInfo.resData);
                    }
                });
                return resList;
            }
            else if (this.throwException) {
                throw new RuntimeException("无法解析非List数据");
            }
            else {
                return resList;
            }
        }
        else if (this.throwException) {
            throw new RuntimeException("不存在的键值。");
        }
        else {
            return resList;
        }
    }

    public Map<String, Object> getMap(String key) {
        return getMap(key, new HashMap<>(0));
    }

    public Map<String, Object> getMap(String key, Map<String, Object> defValue) {
        Object obj = map.get(key);
        if (obj == null) {
            return defValue;
        }
        else {
            try {
                return MapUtil.mapObjectToStringKeyMap(obj);
            }
            catch (Exception ignored) {
                return defValue;
            }
        }
    }

    public static class ResInfo<T> {
        Boolean res;
        T resData;

        public ResInfo(Boolean res, T resData) {
            this.res = res;
            this.resData = resData;
        }
    }

}
