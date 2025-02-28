package com.liuhuiyu.core.time;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
class TimePeriodTest extends AbstractTest {
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
    public void timestampCreate(StringTime data) {
        Timestamp beginTime = TimestampUtil.beginTime(data.beginTime);
        Timestamp endTime = TimestampUtil.beginTime(data.endTime);
        final TimePeriod timePeriod = TimePeriod.timestampCreate(beginTime, endTime);
        if (timePeriod.isValidTime()) {
            LOG.info("开始时间：{}；结束时间：{}；时间点{}在时间范围之{}", timePeriod.getBeginTime(), timePeriod.getEndTime(), data.middleTime, timePeriod.isInTimePeriod(data.middleTime) ? "内" : "外");
        }
        else {
            LOG.info("无法对{}/{}进行日期转换", data.beginTime, data.endTime);
        }
    }

    @DisplayName("时间段交错测试")
    @ParameterizedTest()
    @MethodSource("argumentGenerator")
    public void t(String beg01, String end01, String beg02, String end02, String info) {
        TimePeriod timePeriod1 = TimePeriod.stringCreate(beg01, end01);
        TimePeriod timePeriod2 = TimePeriod.stringCreate(beg02, end02);
        LOG.info("{}:{}", timePeriod1.isTimeStaggered(timePeriod2), info);
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

    static Stream<Arguments> argumentGenerator() {
        return Stream.of(
                Arguments.of("2022-01-01 13:11", "2022-01-01 12:11", "2022-01-01 13:11", "2022-01-01 12:11", "相同时间点"),
                Arguments.of("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 12:17:00", "", "有无效数据"),
                Arguments.of("2022-01-01 13:11", "", "2022-01-01 12:01", "2022-01-01 12:17:00", "有无效数据"),
                Arguments.of("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 12:11", "2022-01-01 11:01", "交错"),
                Arguments.of("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 12:11", "2022-01-01 12:12", "交错"),
                Arguments.of("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-01 10:11", "2022-01-01 14:01", "交错"),
                Arguments.of("2022-01-01 13:11", "2022-01-01 12:01", "2022-01-02 13:11", "2022-01-02 12:01", "不交错")
        );
    }
}