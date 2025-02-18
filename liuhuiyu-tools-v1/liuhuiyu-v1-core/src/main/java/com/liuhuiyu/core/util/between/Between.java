package com.liuhuiyu.core.util.between;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/2/6 15:15
 */
public class Between<T extends Comparable<T>> {
    private T minValue;
    private T maxValue;

    public Between() {
    }

    public Between(T minValue, T maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }
}
