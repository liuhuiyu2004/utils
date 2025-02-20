package com.liuhuiyu.core.util;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-05 9:55
 */
class IgnoredExceptionTest extends AbstractTest {

    @DisplayName("openId解绑")
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("argumentsProvider")
    public void run(Integer value) {
        IgnoredException.run(() -> {
            if (value > 10) {
                throw new RuntimeException("数字过大");
            }
            LOG.info("输入的数字为：{}", value);
        }, (ex) -> LOG.info("{}:{}", ex.getMessage(), value));
    }

    @DisplayName("忽略异常获取默认值")
    @ParameterizedTest()
    @MethodSource("argumentsProvider")
    public void get(Integer value) {
        int v = value > 10 ? -1 : 0;
        final Integer integer = IgnoredException.get(() -> {
            if (value > 10) {
                throw new Exception("");
            }
            Assert.assertTrue(value > 5, "");
            return value;
        }, v);
        LOG.info("{}->{}", value, integer);
    }

    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
                Arguments.arguments(1),
                Arguments.arguments(3),
                Arguments.arguments(16),
                Arguments.arguments(7),
                Arguments.arguments(15)
        );
    }

}