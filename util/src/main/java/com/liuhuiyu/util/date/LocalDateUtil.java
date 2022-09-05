package com.liuhuiyu.util.date;

import com.liuhuiyu.util.asserts.LhyAssert;
import com.liuhuiyu.util.list.ExecutionFunction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 本地日期时间工具
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-05-22 14:39
 */
public class LocalDateUtil {
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR = "yyyy-MM-dd HH";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static String dateToString(LocalDateTime date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static String dateToString(LocalTime date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    //region 常量
    /**
     * 时间分隔符
     * Created DateTime 2022-09-05 10:56
     */
    public static final String TIME_DELIMITER_REGEX = ":";
    /**
     * 日期时间分隔符
     * Created DateTime 2022-09-05 10:56
     */
    public static final String DATE_TIME_DELIMITER_REGEX = " ";
    /**
     * 日期分隔符
     * Created DateTime 2022-09-05 10:56
     */
    public static final String DATE_DELIMITER_REGEX = "[-/\\\\]";

    public static final int PRECISION_YEAR = 1;
    public static final int PRECISION_MONTH = 2;
    public static final int PRECISION_DAY = 3;
    public static final int PRECISION_HOUR = 4;
    public static final int PRECISION_MINUTE = 5;
    public static final int PRECISION_SECOND = 6;
    public static final int PRECISION_NANO = 7;
    public static final int DEFINE_YEAR = 1970;
    public static final int DEFINE_MONTH = 1;
    public static final int DEFINE_DAY = 1;
    public static final int DEFINE_HOUR = 0;
    public static final int DEFINE_MINUTE = 0;
    public static final int DEFINE_SECOND = 0;
    public static final int DEFINE_NANO = 0;
    //endregion

    /**
     * 字符串转日期
     *
     * @param value 字符串
     * @return java.time.LocalDate
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 10:46
     */
    public static LocalDate stringToDate(String value) {
        return stringToDate(value, LocalDate.of(DEFINE_YEAR, DEFINE_MONTH, DEFINE_DAY));
    }

    /**
     * 字符串转日期
     *
     * @param value 字符串
     * @return java.time.LocalDate
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 10:46
     */
    public static LocalDate stringToDate(String value, LocalDate defValue) {
        return stringToDate(value, defValue, PRECISION_DAY);
    }

    /**
     * 字符串转日期
     *
     * @param value 字符串
     * @return java.time.LocalDate
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 10:46
     */
    public static LocalDate stringToDate(String value, LocalDate defValue, int precision) {
        final String[] split = value.split(DATE_DELIMITER_REGEX);
        try {
            int year = getValue(split, 0, precision < PRECISION_YEAR, DEFINE_YEAR);
            int month = getValue(split, 1, precision < PRECISION_MONTH, DEFINE_MONTH);
            int day = getValue(split, 2, precision < PRECISION_DAY, DEFINE_DAY);
            return LocalDate.of(year, month, day);
        }
        catch (Exception ignored) {
            return defValue;
        }
    }

    /**
     * 字符串转时间
     *
     * @param value 字符串
     * @return java.time.LocalTime
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 14:15
     */
    public static LocalTime stringToTime(String value) {
        return stringToTime(value, LocalTime.of(DEFINE_HOUR, DEFINE_MINUTE, DEFINE_SECOND, DEFINE_NANO));
    }

    /**
     * 字符串转时间
     *
     * @param value    字符串
     * @param defValue 默认值
     * @return java.time.LocalTime
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 14:15
     */
    public static LocalTime stringToTime(String value, LocalTime defValue) {
        return stringToTime(value, defValue, PRECISION_MINUTE);
    }

    /**
     * 字符串转时间
     *
     * @param value     字符串
     * @param defValue  默认值
     * @param precision 解析精度
     * @return java.time.LocalTime
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 14:15
     */
    public static LocalTime stringToTime(String value, LocalTime defValue, int precision) {
        final String[] split = value.split(TIME_DELIMITER_REGEX);
        try {
            int hour = getValue(split, 0, precision < PRECISION_HOUR, DEFINE_HOUR);
            int minute = getValue(split, 1, precision < PRECISION_MINUTE, DEFINE_MINUTE);
            int second = getValue(split, 2, precision < PRECISION_SECOND, DEFINE_SECOND);
            int nanoOfSecond = getValue(split, 3, precision < PRECISION_NANO, DEFINE_NANO);
            return LocalTime.of(hour, minute, second, nanoOfSecond);
        }
        catch (Exception ignored) {
            return defValue;
        }
    }

    /**
     * 字符串转日期时间
     *
     * @param value 字符串(默认精确解析到日)
     * @return java.time.LocalDateTime
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 14:04
     */
    public static LocalDateTime stringToDateTime(String value) {
        return stringToDateTime(value, LocalDateTime.of(DEFINE_YEAR, DEFINE_MONTH, DEFINE_DAY, DEFINE_HOUR, DEFINE_MINUTE, DEFINE_SECOND, DEFINE_NANO));
    }

    /**
     * 字符串转日期时间
     *
     * @param value       字符串(默认精确解析到日)
     * @param defDateTime 解析失败默认值
     * @return java.time.LocalDateTime
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 14:04
     */
    public static LocalDateTime stringToDateTime(String value, LocalDateTime defDateTime) {
        return stringToDateTime(value, defDateTime, PRECISION_DAY);
    }

    /**
     * 字符串转日期时间
     *
     * @param value       字符串
     * @param defDateTime 解析失败默认值
     * @param precision   解析精度
     *                    <pre>YEAR_PRECISION:年</pre>
     *                    <pre>MONTH_PRECISION:月</pre>
     *                    <pre>DAY_PRECISION:日</pre>
     *                    <pre>HOUR_PRECISION:时</pre>
     *                    <pre>MINUTE_PRECISION:分</pre>
     *                    <pre>SECOND_PRECISION:秒</pre>
     *                    <pre>NANO_PRECISION:纳秒</pre>
     * @return java.time.LocalDateTime
     * @author LiuHuiYu
     * Created DateTime 2022-09-05 14:04
     */
    public static LocalDateTime stringToDateTime(String value, LocalDateTime defDateTime, int precision) {
        final String[] splitBase = value.split(DATE_TIME_DELIMITER_REGEX);
        try {
            LocalDate localDate = (LocalDate) ExecutionFunction.begin(splitBase)
                    .notSucceedExecution(v -> stringToDate(v[0], null, precision))
                    .orElse(null);
            LhyAssert.assertNotNull(localDate, "");
            LocalTime localTime = (LocalTime) ExecutionFunction.begin(splitBase)
                    .notSucceedExecution(v -> stringToTime(v[1], null, precision))
                    .notSucceedExecution(v -> {
                        LhyAssert.assertTrue(precision < PRECISION_HOUR, "");
                        return LocalTime.MIN;
                    })
                    .orElse(null);
            LhyAssert.assertNotNull(localTime, "");
            return LocalDateTime.of(localDate, localTime);
        }
        catch (Exception ignored) {
            return defDateTime;
        }
    }

    private static int getValue(String[] split, int index, boolean precisionVerify, int defValue) {
        return (int) ExecutionFunction.begin(split)
                .notSucceedExecution(v -> Integer.parseInt(split[index]))
                .notSucceedExecution((v) -> {
                    LhyAssert.assertTrue(precisionVerify, "");
                    return defValue;
                }).get();
    }
}
