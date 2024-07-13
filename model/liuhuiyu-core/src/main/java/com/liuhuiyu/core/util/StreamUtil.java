package com.liuhuiyu.core.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/7/13 12:05
 */
public class StreamUtil {
    /**
     * 区分ByKey<p>
     * 在stream.filter中使用达到过滤重复数据效果
     * author LiuHuiYu<p>
     * Created DateTime 2024/7/13 12:06
     *
     * @param keyExtractor key提取器
     * @return java.util.function.Predicate<T>
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
