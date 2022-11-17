package com.liuhuiyu.util.list;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-06-25 9:02
 */
class IfRunTest extends TestBase {
    @Test
    public void testIf() {
        Integer s = ThreadLocalRandom.current().nextInt(2);
        final Optional<Object> run = IfRun.ifRun(s)
                .ifRun(s.equals(1), this::ss)
                .ifRun(s.equals(0), this::ss2)
                .run();
    }

    public Void ss() {
        return null;
    }

    public Void ss2() {
        return null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"s", "", "null", "onull"})
    public void testNull(String v) {
        String v1 = v.equals("null") ? null : v;
        Optional<String> optional = null;
        if (!"onull".equals(v)) {
            optional = Optional.ofNullable(v1);
        }
        LOG.info("{}:{}", v1, optional);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "ab", "abc", "abcd"})
    public void testIfRun(String v) {
        int i = v.length();
        AtomicReference<Boolean> t = new AtomicReference<>(false);
        final Optional<Object> run = IfRun.ifRun(v)
                .ifRun(i == 1, (s) -> "测试Function[" + s + "]")
                .ifRun(i == 2, () -> "测试Supplier[" + v + "]")
                .ifRun(i == 3, () -> t.set(true))
                .run();

        LOG.info("原始数据：{}；长度：{}；执行信息:{}；执行Runnable:{}", v, i, run.orElse(t.get() ? "测试Runnable" : "未设定的解析类型。"), t.get());
    }
}