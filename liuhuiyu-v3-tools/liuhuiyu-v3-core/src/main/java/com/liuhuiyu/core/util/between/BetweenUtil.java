package com.liuhuiyu.core.util.between;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/2/6 15:17
 */
public class BetweenUtil {
    /**
     * 重叠的闭合间隔检测<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/2/8 9:27
     *
     * @param between1 区间1
     * @param between2 区间2
     * @return boolean
     */
    public static <T extends Comparable<T>> boolean isOverlappingClosedInterval(Between<T> between1, Between<T> between2) {
        return between1.getMaxValue().compareTo(between2.getMinValue()) >= 0 && between1.getMinValue().compareTo(between2.getMaxValue()) <= 0;
    }

    /**
     * 重叠检测<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/2/8 9:27
     *
     * @param between1 区间1
     * @param between2 区间2
     * @return boolean
     */
    public static <T extends Comparable<T>> boolean isOverlapping(Between<T> between1, Between<T> between2) {
        return between1.getMaxValue().compareTo(between2.getMinValue()) > 0 && between1.getMinValue().compareTo(between2.getMaxValue()) < 0;
    }
}
