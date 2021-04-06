package com.liuhuiyu.view;

import java.util.function.Function;

/**
 * 顺序获取对象序列
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-09 17:30
 */
public class ObjectArray {
    private final Object[] objects;
    private int index = 0;

    public ObjectArray(Object obj) {
        this.objects = (Object[]) obj;
    }

    public void reset() {
        this.index = 0;
    }

    public Object get() {
        if (this.index >= objects.length) {
            throw new RuntimeException("获取的数据数量" + this.index + "超出了原始数据长度" + this.objects.length);
        }
        return objects[this.index++];
    }

    public String getString() {
        return getString("");
    }

    public String getString(String defValue) {
        return getT((obj) -> {
            if (obj instanceof String) {
                return (String) obj;
            }
            else {
                return obj.toString();
            }
        }, defValue);
    }

    public Integer getInteger() {
        return getInteger(0);
    }

    public Integer getInteger(Integer defValue) {
        return getT((obj) -> {
            if (obj instanceof Number) {
                return ((Number) obj).intValue();
            }
            else {
                return Integer.parseInt(obj.toString());
            }
        }, defValue);
    }

    public Long getLong() {
        return getLong(0L);
    }

    public Long getLong(Long defValue) {
        return getT((obj) -> {
            if (obj instanceof Number) {
                return ((Number) obj).longValue();
            }
            else {
                return Long.parseLong(obj.toString());
            }
        }, defValue);
    }

    public Boolean getBoolean() {
        return getBoolean(false);
    }

    public Boolean getBoolean(Boolean defValue) {
        return getT((obj) -> {
            if (obj instanceof Boolean) {
                return (Boolean) obj;
            }
            else if (obj instanceof Number) {
                return ((Number) obj).intValue() == 1;
            }
            else {
                return Boolean.parseBoolean(obj.toString());
            }
        }, defValue);
    }

    private <T> T getT(Function<Object, T> function, T defValue) {
        Object obj = this.get();
        if (obj == null) {
            return defValue;
        }
        else {
            try {
                return function.apply(obj);
            }
            catch (Exception ex) {
                return defValue;
            }
        }
    }
}
