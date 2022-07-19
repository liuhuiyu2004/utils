package com.liuhuiyu.util.date;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-07-09 12:53
 */
class TimestampUtilTest {
    @Test
    public void isTimestampString() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        String value = TimestampUtil.toString(timestamp, TimestampUtil.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        if (TimestampUtil.isTimestampString(value)) {
            System.out.println("不是日期格式");
        }
    }

}