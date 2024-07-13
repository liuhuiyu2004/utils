package com.liuhuiyu.core.util.interval;

import java.util.ArrayList;
import java.util.List;

/**
 * 区间
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/7/7 18:15
 */
public class Interregional <T> {
    private T minValue;
    private T maxValue;
    private List<T> elementList = new ArrayList<>();

    public Interregional() {
    }

    public Interregional(T minValue, T maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }

    public void setElementList(List<T> elementList) {
        this.elementList = elementList;
    }

    public List<T> getElementList() {
        return elementList;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }
}