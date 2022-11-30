package com.liuhuiyu.util.list;

import com.liuhuiyu.test.TestBase;
import com.liuhuiyu.util.asserts.LhyAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-05 14:22
 */
class ExecutionFunctionTest extends TestBase {

    @Test
    public void test01() {
        for (int i = -10; i < 10; i++) {
            try {
                final String s = this.executionFunction1(i);
                LOG.info("{}:{}", i, s);
            }
            catch (Exception e) {
                LOG.error("{}:{}", i, e);
            }
        }
    }
    @Test
    public void test02() {
        for (int i = -10; i < 10; i++) {
            try {
                final String s = this.executionFunction(i);
                LOG.info("{}:{}", i, s);
            }
            catch (Exception e) {
                LOG.error("{}:{}", i, e);
            }
        }
    }
    private String executionFunction1(int i) {
        return (String) ExecutionFunction.begin(i)
                .notSucceedExecution((v) -> {
                    LhyAssert.assertTrue(v > 3, "e1");
                    return "a";
                })
                .notSucceedExecution((v) -> {
                    LhyAssert.assertTrue(v > 2, "e2");
                    return "b";
                })
                .notSucceedExecution((v) -> {
                    LhyAssert.assertTrue(v > 1, "e3");
                    return "c";
                })
                .notSucceedExecution((v) -> {
                    LhyAssert.assertTrue(v > 0, "e4");
                    return "d";
                })
                .notSucceedExecution(() -> {
                    LhyAssert.assertTrue(i > -1, "e5");
                    return "f";
                })
                .get();
    }
    private String executionFunction(int i) {
        return (String) ExecutionFunction.begin()
                .notSucceedExecution(() -> {
                    LhyAssert.assertTrue(i > 3, "e1");
                    return "a";
                })
                .notSucceedExecution(() -> {
                    LhyAssert.assertTrue(i > 2, "e2");
                    return "b";
                })
                .notSucceedExecution(() -> {
                    LhyAssert.assertTrue(i > 1, "e3");
                    return "c";
                })
                .notSucceedExecution(() -> {
                    LhyAssert.assertTrue(i > 0, "e4");
                    return "d";
                })
                .notSucceedExecution(() -> {
                    LhyAssert.assertTrue(i > -1, "e5");
                    return "f";
                })
                .get();
    }
}