package com.liuhuiyu.core.util.interval;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 时间区间合并
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/7/7 18:16
 */
public class IntervalMerger {
    /**
     * 获取时间段列表<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/7/7 18:16
     *
     * @param intervals     要获取的列表
     * @param sort          排序规则（按照最小到最大排序）
     * @param overlap       两个元素是否重叠规则(第一个元素的最大值是否大于第二个元素的最小值)
     * @param maxComparator 最大值比较规则（第一个值的最大值大于第二个值的最大值）
     * @return java.util.List<com.liuhuiyu.core.util.interval.Interregional < T>>
     */
    public static <T> List<Interregional<T>> mergeIntervals(List<T> intervals, Comparator<T> sort, Comparator<T> overlap, Comparator<T> maxComparator) {
        List<Interregional<T>> resList = new ArrayList<>();
        // 如果列表为空或只有一个元素，直接返回
        if (intervals == null || intervals.isEmpty()) {
            return null;
        }
        else if (intervals.size() == 1) {
            final Interregional<T> tInterregional = new Interregional<>(intervals.get(0), intervals.get(0));
            resList.add(tInterregional);
            return resList;
        }
        // 按照开始时间排序
        intervals.sort(sort);
        Interregional<T> res = new Interregional<>();
        res.setMinValue(intervals.get(0));
        res.setMaxValue(intervals.get(0));
        res.getElementList().add(intervals.get(0));
        resList.add(res);
        for (int i = 1; i < intervals.size(); i++) {
            T next = intervals.get(i);
            // 如果下一个时间段的开始时间小于等于当前时间段的结束时间，说明它们重叠
            if (overlap.compare(res.getMinValue(), next) >= 0) {
                res.getElementList().add(next);
                res.setMaxValue(maxComparator.compare(res.getMaxValue(), next) > 0 ? res.getMaxValue() : next);
            }
            else {
                // 否则，将下一个时间段设为当前时间段，并添加到merged列表中
                res = new Interregional<>();
                res.setMinValue(next);
                res.setMaxValue(next);
                res.getElementList().add(next);
                resList.add(res);
            }
        }
        return resList;
    }

    /**
     * 获取首个时间段数据<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/7/7 13:12
     *
     * @param intervals     要获取的列表
     * @param sort          排序规则（按照最小到最大排序）
     * @param overlap       两个元素是否重叠规则(第一个元素的最大值是否大于第二个元素的最小值)
     * @param maxComparator 最大值比较规则（第一个值的最大值大于第二个值的最大值）
     * @return com.liuhuiyu.core.util.interval.Interregional<T>
     */
    public static <T> Interregional<T> getFirstMergeIntervals(List<T> intervals, Comparator<T> sort, Comparator<T> overlap, Comparator<T> maxComparator) {
        // 如果列表为空或只有一个元素，直接返回
        if (intervals == null || intervals.isEmpty()) {
            return null;
        }
        else if (intervals.size() == 1) {
            return new Interregional<>(intervals.get(0), intervals.get(0));
        }
        // 按照开始时间排序
        intervals.sort(sort);
        Interregional<T> res = new Interregional<>();
        res.setMinValue(intervals.get(0));
        res.setMaxValue(intervals.get(0));
        res.getElementList().add(intervals.get(0));
        for (int i = 1; i < intervals.size(); i++) {
            T next = intervals.get(i);
            // 如果下一个时间段的开始时间小于等于当前时间段的结束时间，说明它们重叠
            if (overlap.compare(res.getMinValue(), next) >= 0) {
                res.getElementList().add(next);
                res.setMaxValue(maxComparator.compare(res.getMaxValue(), next) > 0 ? res.getMaxValue() : next);
            }
            else {
                // 否则，将下一个时间段设为当前时间段，并添加到merged列表中
                return res;
            }
        }
        return res;
    }

}