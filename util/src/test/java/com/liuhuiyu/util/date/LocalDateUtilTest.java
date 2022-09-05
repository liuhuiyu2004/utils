package com.liuhuiyu.util.date;


import org.apache.juli.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-07 14:10
 */
public class LocalDateUtilTest {
    private static final Logger LOG = LogManager.getLogger(LocalDateUtilTest.class);

    @Test
    public void testStringToDate() {
        String dateStr = "2021-02-11";
        LocalDateTime time2 = LocalDateUtil.stringToDateTime(dateStr);
        LOG.info(time2);
        LocalDateTime time1 = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-d H:m:s"));
        LOG.info(time1);
    }

    @Test
    public void t1() {
        String dateStr = "2021-02-11 9";
        LocalDateTime time4 = LocalDateUtil.stringToDateTime(dateStr);
        LOG.error(time4);
        LocalDateTime time3 = LocalDateUtil.stringToDateTime(dateStr, LocalDateTime.now(), LocalDateUtil.MINUTE_PRECISION);
        LOG.error(time3);
        LocalDateTime time2 = LocalDateUtil.stringToDateTime(dateStr, LocalDateTime.now(), LocalDateUtil.HOUR_PRECISION);
        LOG.error(time2);
        LocalDateTime time1 = LocalDateUtil.stringToDateTime(dateStr, LocalDateTime.now(), LocalDateUtil.DAY_PRECISION);
        LOG.error(time1);
    }

    @Test
    public void stringToTime() {
        String[] vs = new String[]{"","1","1:6","01:6:15","01:6:15:77"};
        for (String v : vs) {
            LOG.error("str:{}", v);
            LocalTime localTime = LocalDateUtil.stringToTime(v);
            LOG.error("原始:{}",localTime);
            localTime = LocalDateUtil.stringToTime(v,LocalTime.now());
            LOG.error("当前时间:{}",localTime);
            localTime = LocalDateUtil.stringToTime(v,LocalTime.now(),LocalDateUtil.HOUR_PRECISION);
            LOG.error("小时精度:{}",localTime);
            localTime = LocalDateUtil.stringToTime(v,LocalTime.now(),LocalDateUtil.MINUTE_PRECISION);
            LOG.error("分钟精度:{}",localTime);
            localTime = LocalDateUtil.stringToTime(v,LocalTime.now(),LocalDateUtil.SECOND_PRECISION);
            LOG.error("秒精度:{}",localTime);
            localTime = LocalDateUtil.stringToTime(v,LocalTime.now(),LocalDateUtil.NANO_PRECISION);
            LOG.error("纳秒精度:{}",localTime);
        }
    }
}