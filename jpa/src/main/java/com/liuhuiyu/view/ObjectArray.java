package com.liuhuiyu.view;

/**
 * 顺序获取对象序列
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
        return get().toString();
    }

    public Integer getInteger() {
        return getInteger(0);
    }

    public Integer getInteger(Integer defValue) {
        Object obj = this.get();
        if (obj == null) {
            return defValue;
        }
        try {
            return Integer.parseInt(obj.toString());
        }
        catch (NumberFormatException ex) {
            return defValue;
        }
    }
}
