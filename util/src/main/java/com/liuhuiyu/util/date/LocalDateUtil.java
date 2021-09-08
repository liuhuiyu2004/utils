package com.liuhuiyu.util.date;

import com.liuhuiyu.util.asserts.LhyAssert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static LocalDateTime stringToDateTime(String dateTimeStr) {
        return stringToDateTime(dateTimeStr,null);
    }

    public static LocalDateTime stringToDateTime(String dateTimeStr, LocalDateTime defDateTime) {
        try {
            LhyAssert.assertNotNull(dateTimeStr, new DateTimeParseException("格式错误", "yyyy-mm-dd hh:MM:ss", -1));
            final String dateTimeSeparator = " ";
            final String dateSeparator = "-";
            final String timeSeparator = ":";
            int year, month, dayOfMonth, hour = 0, minute = 0, second = 0;
            //字段拆解
            String[] dateTimeCut = dateTimeStr.split(dateTimeSeparator);
            LhyAssert.assertTrue(dateTimeCut.length <= 2 && dateTimeCut.length > 0, new DateTimeParseException("格式错误", "yyyy-mm-dd hh:MM:ss", -1));
            String[] dateStr = dateTimeCut[0].split(dateSeparator);
            LhyAssert.assertTrue(dateStr.length == 3, new DateTimeParseException("格式错误", "yyyy-mm-dd hh:MM:ss", -1));
            try {
                year = Integer.parseInt(dateStr[0]);
                month = Integer.parseInt(dateStr[1]);
                dayOfMonth = Integer.parseInt(dateStr[2]);
            }
            catch (NumberFormatException e) {
                throw new DateTimeParseException("格式错误", "yyyy-mm-dd hh:MM:ss", -1);
            }
            if (dateTimeCut.length == 2) {
                String[] timeStr = dateTimeCut[1].split(timeSeparator);
                if (timeStr.length >= 1) {
                    hour = Integer.parseInt(timeStr[0]);
                }
                if (timeStr.length >= 2) {
                    minute = Integer.parseInt(timeStr[1]);
                }
                if (timeStr.length >= 3) {
                    second = Integer.parseInt(timeStr[2]);
                }
            }
            return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        }
        catch (Exception e) {
            if (defDateTime == null) {
                throw e;
            }
            else {
                return defDateTime;
            }
        }
    }
}
