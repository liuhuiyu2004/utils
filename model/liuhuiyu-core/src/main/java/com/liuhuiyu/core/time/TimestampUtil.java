package com.liuhuiyu.core.time;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 15:55
 */
public class TimestampUtil {
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR = "yyyy-MM-dd HH";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 转换成开始时间
     *
     * @param value 字符串
     * @return java.sql.Timestamp
     */
    public static Timestamp beginTime(String value) {
        try {
            String tempV = value;
            if (tempV.length() == FORMAT_YEAR_MONTH_DAY.length()) {
                tempV += " 00:00:00";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR.length()) {
                tempV += ":00:00";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE.length()) {
                tempV += ":00";
            }
            return Timestamp.valueOf(tempV);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换成结束时间
     *
     * @param value 字符串
     * @return java.sql.Timestamp
     */
    public static Timestamp endTime(String value) {
        try {
            String tempV = value;
            if (tempV.length() == FORMAT_YEAR_MONTH_DAY.length()) {
                tempV += " 23:59:59.999";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR.length()) {
                tempV += ":59:59.999";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE.length()) {
                tempV += ":59.999";
            }
            else if (tempV.length() == FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND.length()) {
                tempV += ".999";
            }
            return Timestamp.valueOf(tempV);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 可以转换为 Timestamp 的字符串
     *
     * @param value 字符串
     * @return boolean
     */
    public static boolean isTimestampString(String value) {
        return endTime(value) != null;
    }

    public static String toString(Timestamp timestamp, String format) {
        try {
            return new SimpleDateFormat(format).format(timestamp);
        }
        catch (Exception ex) {
            return "";
        }
    }
}
