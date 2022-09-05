package com.liuhuiyu.util.list;

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
class ExecutionFunctionTest {
    private static final Logger LOG = LogManager.getLogger(ExecutionFunctionTest.class);

    @Test
    public void test01() {
        for (int i = -10; i < 10; i++) {
            try {
                final String s = this.executionFunction(i);
                LOG.error("{}:{}", i, s);
            }
            catch (Exception e) {
                LOG.error("{}:{}", i, e);
            }
        }
    }

    private String executionFunction(int i) {
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
                }).notSucceedExecution((v) -> {
                    LhyAssert.assertTrue(v > 0, "e4");
                    return "d";
                }).get();
    }
}