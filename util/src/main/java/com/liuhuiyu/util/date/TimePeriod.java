package com.liuhuiyu.util.date;

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

    public TimePeriod(String beginTime, String endTime) {
        Timestamp b = TimestampUtil.beginTime(beginTime);
        Timestamp e = TimestampUtil.beginTime(endTime);
        if (b == null || e == null) {
            this.beginTime = null;
            this.endTime = null;
        }
        else {
            this.beginTime = b.before(e) ? b : e;
            this.endTime = b.after(e) ? TimestampUtil.endTime(beginTime) : TimestampUtil.endTime(endTime);
        }
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public boolean isValidTime() {
        return beginTime != null;
    }
}
