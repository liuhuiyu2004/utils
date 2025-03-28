package com.liuhuiyu.spring.util;

import com.liuhuiyu.core.lang.function_interface.MapToT;
import com.liuhuiyu.core.lang.function_interface.ObjectToT;
import com.liuhuiyu.core.map.MapHelp;
import com.liuhuiyu.core.map.MapUtil;
import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.web.help.Result;
import com.liuhuiyu.web.lang.ResultException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能<p>
 * Created on 2025/3/28 21:06
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class ResultUtil {

    private static final String FLAG_KEY = "flag";
    private static final String MSG_KEY = "msg";
    private static final String DATA_KEY = "data";

    @Deprecated
    public static <T> @NotNull Result<T> ofMap(Map<String, Object> map, Class<T> clazz) {
        Result<T> result = new Result<>();
        if (map.containsKey(FLAG_KEY)) {
            result.setFlag(((Number) map.get(FLAG_KEY)).intValue());
        }
        else {
            throw new RuntimeException("缺少关键字" + FLAG_KEY);
        }
        if (map.containsKey(MSG_KEY)) {
            result.setMsg(map.get(MSG_KEY).toString());
        }
        else {
            throw new RuntimeException("缺少关键字" + MSG_KEY);
        }
        if (map.containsKey(DATA_KEY)) {
            if (clazz.isInstance(map.get(DATA_KEY))) {
                result.setData(clazz.cast(map.get(DATA_KEY)));
            }
            else {
                throw new RuntimeException("Map 关键字‘" + DATA_KEY + "’无法转换为当前类型。");
            }
        }
        else {
            throw new RuntimeException("缺少关键字" + DATA_KEY);
        }
        return result;
    }

    /**
     * 获取ResultMap模型中的数据
     *
     * @param map 传入数据
     * @return java.lang.Object
     */
    public static Object getResultObj(Map<String, Object> map) {
        return getValueOfResultMap(map, o -> o);
    }

    /**
     * 获取ResultMap模型中的数据
     *
     * @param map    传入数据
     * @param mapToT 转换函数
     * @param <T>    转换类型
     * @return java.util.List&lt;T> 返回转换后的数组（过滤null数据）
     */
    public static <T> List<T> getResultToList(Map<String, Object> map, MapToT<T> mapToT) {
        Assert.assertNotNull(mapToT, new ResultException("不能传入null解析方法mapToT。"));
        Assert.assertNotNull(map, new ResultException("传入null值,无法进行解析map。"));
        ArrayList<?> arrayList = getValueOfResultMap(map, o -> {
            Assert.assertTrue(o instanceof ArrayList, new ResultException("对象无法解析成列表"));
            return (ArrayList<?>) o;
        });
        List<T> list = new ArrayList<>(arrayList.size());
        for (Object obj : arrayList) {
            Map<String, Object> itemMap = MapUtil.mapObjectToStringKeyMap(obj);
            final T t = mapToT.mapToT(itemMap);
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 获取ResultMap模型中的数据
     *
     * @param map    传入数据
     * @param mapToT 转换函数
     * @param <T>    转换类型
     * @return T 返回转换后的结果
     */
    public static <T> T getResultData(Map<String, Object> map, MapToT<T> mapToT) {
        Assert.assertNotNull(mapToT, new ResultException("不能传入null解析方法mapToT。"));
        Object obj = getValueOfResultMap(map, o -> o);
        Map<String, Object> itemMap = MapUtil.mapObjectToStringKeyMap(obj);
        return mapToT.mapToT(itemMap);
    }

    /**
     * 将map转换成Result
     *
     * @param map Result的map结构
     * @return com.liuhuiyu.util.model.Result&lt;java.lang.Object> 返回Result结构
     */
    public static Result<Object> getResult(Map<String, Object> map) {
        Assert.assertNotNull(map, new ResultException("传入null值,无法进行解析map。"));
        Result<Object> result = new Result<>();
        Assert.assertTrue(map.containsKey(FLAG_KEY), new ResultException("flag信息有效判定字段不存在。"));
        result.setFlag((int) map.get(FLAG_KEY));
        if (map.containsKey(MSG_KEY)) {
            result.setMsg((String) map.get(MSG_KEY));
        }
        if (map.containsKey(DATA_KEY)) {
            result.setData(map.get(DATA_KEY));
        }
        return result;
    }

    /**
     * 获取ResultMap的数据
     *
     * @param map       Result Map形式
     * @param objectToT 数据转换函数
     * @param <T>       转换后的类型
     * @return T 转换后的结果
     */
    public static <T> T getValueOfResultMap(Map<String, Object> map, ObjectToT<T> objectToT) {
        Assert.assertNotNull(objectToT, new ResultException("不能传入null解析方法mapToT。"));
        Assert.assertNotNull(map, new ResultException("传入null值,无法进行解析map。"));
        Result<Object> result = getResult(map);
        Assert.assertTrue(result.isSuccess(), result.getMsg());
        return objectToT.objectToT(result.getData());
    }

    /**
     * 获取ResultMap模型中的分页数据
     *
     * @param map    原始map数据
     * @param mapToT 转换函数
     * @param <T>    泛型
     * @return org.springframework.data.domain.PageImpl<T>
     */
    @Contract("_, _ -> new")
    public static <T> @NotNull PageImpl<T> getResultToPageImpl(Map<String, Object> map, MapToT<T> mapToT) {
        Assert.assertNotNull(mapToT, new ResultException("不能传入null解析方法mapToT。"));
        Result<Object> result = getResult(map);
        if (result.isSuccess()) {
            MapHelp mapUtil = new MapHelp(MapUtil.mapObjectToStringKeyMap(result.getData()));
            int number = mapUtil.getIntegerValue("number", 0);
            int size = mapUtil.getIntegerValue("size", 0);
            PageRequest pageable = PageRequest.of(number, size);
            long totalElements = mapUtil.getLongValue("totalElements", 0L);
            List<T> list = mapUtil.getListValue("content", obj -> mapToT.mapToT(MapUtil.mapObjectToStringKeyMap(obj)));
            return new PageImpl<>(list, pageable, totalElements);
        }
        else {
            throw new RuntimeException(result.getMsg());
        }
    }
}
