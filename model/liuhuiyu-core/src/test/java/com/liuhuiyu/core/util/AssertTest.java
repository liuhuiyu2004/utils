package com.liuhuiyu.core.util;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-17 15:09
 */
class AssertTest extends TestBase {
    @DisplayName("断言测试")
    @ParameterizedTest()
    @MethodSource("dataGenerator")
    public void test(Object obj) {
        Assert.exceptionProxy(RuntimeException::new);
        if (obj instanceof Boolean) {
            Assert.assertTrue((Boolean) obj, "obj!=true");
            Assert.assertFalse((Boolean) obj, new RuntimeException("obj==true"));
        }
        else if (obj instanceof Integer) {
            Assert.assertFalse((Integer) obj > 1, "obj > 1");
        }
        else if (obj instanceof String) {
            Assert.assertLen((String) obj, 5, 10, "长度错误。");
            Assert.assertLen((String) obj, 15, 20, new RuntimeException("长度错误。"));
        }
        Assert.assertNull(obj, "obj!=null");
        Assert.assertNull(obj, new RuntimeException("obj!=null"));
        Assert.assertNotNull(obj, "obj==null");
    }

    static Stream<Object> dataGenerator() {
        List<Object> objects = new ArrayList<>();
        objects.add("1");
        objects.add(null);
        objects.add(true);
        objects.add(1);
        return objects.stream();
    }
}