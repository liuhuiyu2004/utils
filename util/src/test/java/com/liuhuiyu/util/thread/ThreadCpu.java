package com.liuhuiyu.util.thread;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-30 20:25
 */
@Log4j2
public class ThreadCpu {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;
    int i = 0;

    @Test
    public void test() throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread other = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();
            other.start();
            one.join();
            other.join();
            String result = "第" + i + "次 (" + x + "," + y + "）";
            // 出现00组合 证明cpu乱序执行了
            if (x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                //System.out.println(result);
            }
        }
    }
}
