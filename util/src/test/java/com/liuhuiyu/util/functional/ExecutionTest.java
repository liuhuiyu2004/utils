package com.liuhuiyu.util.functional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-07-09 13:20
 */
class ExecutionTest {
    @Test
    public void run() {
        Execution execution = () -> {
            System.out.println("测试执行");
        };
        execution.run();
    }

}