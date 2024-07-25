package com.liuhuiyu.core.util.interval;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/7/25 16:48
 */
class InterregionalTest extends TestBase {
    @MethodSource("generateTimeRanges")
    @ParameterizedTest
    @DisplayName("时间段测试")
    public void generateTimeRanges(LocalTime startTime, LocalTime endTime) {

        final Interregional<LocalDateTime> localDateTimeInterregional = Interregional.generateTimeRanges(startTime, endTime);
        LOG.info("{}-{}", localDateTimeInterregional.getMinValue(),localDateTimeInterregional.getMaxValue());
    }

    static Stream<Arguments> generateTimeRanges() {
        return Stream.of(
                Arguments.of(LocalTime.of(8, 0), LocalTime.of(10, 0)),
                Arguments.of(LocalTime.of(17, 50), LocalTime.of(19, 50)),
                Arguments.of(LocalTime.of(8, 0), LocalTime.of(2, 0)),
                Arguments.of(LocalTime.of(18, 0), LocalTime.of(10, 0))
        );
    }
}