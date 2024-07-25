package com.liuhuiyu.core.util.interval;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 区间
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/7/7 18:15
 */
public class Interregional<T> {
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

    /**
     * 生成以当前时间为节点的有效工作时间段<p>
     * 随着时间流逝确保生成的时间段最先生效<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/7/25 17:11
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return com.liuhuiyu.core.util.interval.Interregional<java.time.LocalDateTime>
     */
    public static Interregional<LocalDateTime> generateTimeRanges(LocalTime startTime, LocalTime endTime) {
        return generateTimeRanges(LocalDateTime.now(), startTime, endTime);
    }

    /**
     * 生成以指定时间为节点的有效工作时间段<p>
     * 随着时间流逝确保生成的时间段最先生效<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/7/25 17:15
     *
     * @param currentDateTime 指定时间点
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @return com.liuhuiyu.core.util.interval.Interregional<java.time.LocalDateTime>
     */
    public static Interregional<LocalDateTime> generateTimeRanges(LocalDateTime currentDateTime, LocalTime startTime, LocalTime endTime) {
        //判断时间是否跨天
        int day = endTime.isBefore(startTime) ? 1 : 0;
        //生成开始时间
        LocalDateTime startDateTime = LocalDate.now().atTime(startTime);
        LocalDateTime endDateTime = LocalDate.now().plusDays(day).atTime(endTime);
        //如果当前时间在结束时间之后就是第二天的数据
        if (currentDateTime.isAfter(endDateTime)) {
            startDateTime = startDateTime.plusDays(1);
            endDateTime = endDateTime.plusDays(1);
        }
        return new Interregional<>(startDateTime, endDateTime);
    }
}