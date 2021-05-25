package com.liuhuiyu.util.date;

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
    public static String dateToString(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }
    public static String dateToString(LocalDateTime date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }
    public static String dateToString(LocalTime date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }
}
