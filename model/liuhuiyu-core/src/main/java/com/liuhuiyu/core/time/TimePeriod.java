package com.liuhuiyu.core.time;

import java.sql.Timestamp;

/**
 * 时间段信息
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 15:54
 */
public class TimePeriod {
    final Timestamp beginTime;
    final Timestamp endTime;

    private TimePeriod(Timestamp beginTime, Timestamp endTime) {
        if (beginTime == null || endTime == null) {
            this.beginTime = null;
            this.endTime = null;
        }
        else {
            this.beginTime = beginTime.before(endTime) ? beginTime : endTime;
            this.endTime = beginTime.after(endTime) ? beginTime : endTime;
        }
    }

    /**
     * 使用开始结束时间字符串创建
     *
     * @param beginTime 开始时间（字符串）
     * @param endTime   结束时间（字符串）
     * @return com.liuhuiyu.core.time.TimePeriod
     * @author LiuHuiYu
     * Created DateTime 2023-01-13 10:03
     */
    public static TimePeriod stringCreate(String beginTime, String endTime) {
        Timestamp b = TimestampUtil.beginTime(beginTime);
        Timestamp e = TimestampUtil.beginTime(endTime);
        if (b == null || e == null) {
            return new TimePeriod(null, null);
        }
        else {
            if (b.before(e)) {
                return new TimePeriod(b, TimestampUtil.endTime(endTime));
            }
            else {
                return new TimePeriod(e, TimestampUtil.endTime(beginTime));
            }
        }
    }

    /**
     * 使用开始结束时间Timestamp串创建
     *
     * @param beginTime 开始时间（Timestamp）
     * @param endTime   结束时间（Timestamp）
     * @return com.liuhuiyu.core.time.TimePeriod
     * @author LiuHuiYu
     * Created DateTime 2023-01-13 10:03
     */
    public static TimePeriod timestampCreate(Timestamp beginTime, Timestamp endTime) {
        return new TimePeriod(beginTime, endTime);
    }

    /**
     * 时间点在当前时间段内部（不包含边界）
     *
     * @param timestamp 检测时间点
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2022-11-29 10:49
     */
    public boolean isInTimePeriod(Timestamp timestamp) {
        return beginTime.before(timestamp) && endTime.after(timestamp);
    }

    /**
     * 获取开始时间
     *
     * @return java.sql.Timestamp
     * @author LiuHuiYu
     * Created DateTime 2023-01-13 10:03
     */
    public Timestamp getBeginTime() {
        return beginTime;
    }

    /**
     * 获取结束时间
     *
     * @return java.sql.Timestamp
     * @author LiuHuiYu
     * Created DateTime 2023-01-13 10:03
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * 判断时间段是否有效
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-01-13 10:02
     */
    public boolean isValidTime() {
        return beginTime != null;
    }
}
