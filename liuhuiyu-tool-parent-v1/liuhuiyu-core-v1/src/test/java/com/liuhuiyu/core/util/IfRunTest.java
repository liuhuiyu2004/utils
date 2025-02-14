package com.liuhuiyu.core.util;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-27 11:55
 */
class IfRunTest extends AbstractTest {
    @DisplayName("测试条件执行")
    @ParameterizedTest()
    @MethodSource("argumentsGenerator")
    public void test(Object obj) {
        LOG.info("输入值：{}", obj);
        Object value = "".equals(obj) ? null : obj;
        IfRun<Object, Object> ifRun = "".equals(obj) ? IfRun.create() : IfRun.create(value);
        IfRun<Object, Object> ifRun1 = ifRun
//                .elseIf(value instanceof Integer, "Integer")
                .elseIf(value instanceof Long, () -> "Long")
                .elseIf(value instanceof String, (v) -> "String[" + v + "]")
                .elseIf(value instanceof Character, () -> LOG.info("char"));
        final Object v0 = ifRun1.getOptionalResults();
        final Object v1 = ifRun1.orElse("指定值");
        final Object v2 = ifRun1.orElse(() -> "Supplier");
        final Object v3 = ifRun1.orElse((a) -> "Function->" + a);
        ifRun.orElse(() -> LOG.info("Runnable"));
        LOG.info("{},{},{},{}", v0, v1, v2, v3);
        final Object v4 = ifRun.orElseThrow(() -> new RuntimeException("异常"));
        LOG.info("{}", v4);
    }

    @Test
    public void test2() {
        String info = "a";
        final Object o = IfRun.create()
                .elseIf(false, () -> this.ff(info+"1"))
//                .elseIf(false, this.ff(info+"2"))
                .orElse(() -> "执行else");
        LOG.info("{}",o);
    }

    private String ff(String info) {
        LOG.info("执行了ff");
        return info;
    }

    public static Stream<Arguments> argumentsGenerator() {
        return Stream.of(
                Arguments.arguments(1),
                Arguments.arguments("a"),
                Arguments.arguments(true),
                Arguments.arguments(0L),
                Arguments.arguments('c'),
                Arguments.arguments(""),
                Arguments.arguments(100L)
        );
    }
}