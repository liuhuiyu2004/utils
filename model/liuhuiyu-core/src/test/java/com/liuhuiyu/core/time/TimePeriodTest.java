package com.liuhuiyu.core.time;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-13 10:12
 */
class TimePeriodTest extends TestBase {
    @DisplayName("时间段测试")
    @ParameterizedTest()
    @MethodSource("stringTimeGenerator")
    public void stringTimePeriod(StringTime data) {
        final TimePeriod timePeriod = TimePeriod.stringCreate(data.beginTime, data.endTime);
        if (timePeriod.isValidTime()) {
            LOG.info("开始时间：{}；结束时间：{}；时间点{}在时间范围之{}", timePeriod.getBeginTime(), timePeriod.getEndTime(), data.middleTime, timePeriod.isInTimePeriod(data.middleTime) ? "内" : "外");
        }
        else {
            LOG.info("无法对{}/{}进行日期转换", data.beginTime, data.endTime);
        }
    }
    @DisplayName("时间段测试")
    @ParameterizedTest()
    @MethodSource("stringTimeGenerator")
    public void timestampCreate(StringTime data){
        Timestamp beginTime=TimestampUtil.beginTime(data.beginTime);
        Timestamp endTime=TimestampUtil.beginTime(data.endTime);
        final TimePeriod timePeriod = TimePeriod.timestampCreate(beginTime, endTime);
        if (timePeriod.isValidTime()) {
            LOG.info("开始时间：{}；结束时间：{}；时间点{}在时间范围之{}", timePeriod.getBeginTime(), timePeriod.getEndTime(), data.middleTime, timePeriod.isInTimePeriod(data.middleTime) ? "内" : "外");
        }
        else {
            LOG.info("无法对{}/{}进行日期转换", data.beginTime, data.endTime);
        }
    }

    static class StringTime {
        String beginTime;
        String endTime;
        Timestamp middleTime;

        public StringTime(String beginTime, String endTime, String middleTime) {
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.middleTime = Timestamp.valueOf(middleTime);
        }
    }

    static Stream<StringTime> stringTimeGenerator() {
        List<StringTime> list = new ArrayList<>();
        list.add(new StringTime("2022-1-1 13:11", "2022-1-1 12:11", "2022-01-01 12:17:00"));
        list.add(new StringTime("2022-01-01 13:11", "2022-01-01 12:1", "2022-01-01 12:17:00"));
        list.add(new StringTime("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 12:17:00"));
        list.add(new StringTime("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 13:17:00"));
        list.add(new StringTime("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 12:01:00"));
        list.add(new StringTime("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 13:11:00"));
        return list.stream();
    }
}