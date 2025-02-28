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
 * Created DateTime 2023-11-27 19:37
 */
class PasswordComplexTest extends AbstractTest {
    @DisplayName("密码复杂度")
    @ParameterizedTest
    @MethodSource("getPasswordStream")
    public void test01(String password) {
        final PasswordComplex complex = PasswordComplex.Builder
                .createPasswordComplex(password)
                .withPasswordMinLength(6)
                .withPasswordMinComplexity(3)
                .build();
        LOG.info("原始：{}；长度校验：{}；密码复杂度：{}；校验有效：{}", password, complex.validLength(), complex.getComplexity(), complex.validPassword());
    }

    static Stream<Arguments> getPasswordStream() {
        return Stream.of(
                Arguments.of("abcdef"),
                Arguments.of("ABCDEFGH"),
                Arguments.of("123456"),
                Arguments.of("!@#$%^&*"),
                Arguments.of("abc123"),
                Arguments.of("1@Aa23")
        );
    }
}