package com.liuhuiyu.util.exception;

import com.liuhuiyu.util.thread.ThreadUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-05 15:59
 */
@Log4j2
public class RetryUtilTest {
//    @Test
//    public void t1() {
//        time = System.currentTimeMillis();
//        String s = s1(time, "abc");
//        log.info("返回信息：{}", s);
//    }
//
//    long time;

//    private String s1(long time, String s) {
//        log.info("进入时间{}", time);
//        getTime();
//        long newTime = System.currentTimeMillis();
//        log.info("{},{}", time, newTime);
//        if (newTime > time + 1000) {
//            return s;
//        }
//        Object o = RetryUtil.setRetryTimes(13).retry(s);
//        if (o != null) {
//            return o.toString();
//        }
//        throw new RuntimeException("错误");
//    }

    private long getTime() {
        log.info("gettime");
        return System.currentTimeMillis();
    }

    private int retryTimes = 3;

    @Test
    public void upperMethod() {
        method("123", "456");
    }

    public void method(String param1, String param2) {
        System.out.println(param1 + param2);

        // 其他一些操作，但是没有得到预期的返回结果，或者抛出异常
        boolean isException = true;
        if (isException && retryTimes > 0) {
            retryTimes--;
            method(param1, param2);
        }
    }

    @Test
    public void upperMethod2() {
        method(3, "123", "456");
    }

    public void method(int retryTimes, String param1, String param2) {
        System.out.println(param1 + param2);

        // 其他一些操作，但是没有得到预期的返回结果，或者抛出异常
        boolean isException = true;
        if (isException && retryTimes > 0) {
            method(--retryTimes, param1, param2);
        }
    }

    //    @Test
//    public void mainMethod() throws InterruptedException {
//        String s = subMethod("123", "456");
//        log.info(s);
//    }
//
//    public String subMethod(String param1, String param2) throws InterruptedException {
//        long t = System.currentTimeMillis();
//        log.info("begin:{},{},{}", param1, param2, t);
//        Thread.sleep(100);
//        new RetryUtil().retry(10, param1, param2);
//        long t2 = System.currentTimeMillis();
//        log.info("end:{},{},{}-{}", param1, param2, t, t2);
//        return param1 + param2 + t;
//    }
    @Test
    public void retry() {
        Map<String, Object> map = new HashMap<>(1);
        long time = System.currentTimeMillis();
        RetryUtil.retry(3, () -> {
            try {
                Thread.sleep(600);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put("res", s(time));
            return false;
        });
        log.info(map);
    }

    @Test
    public void retry2() {
        Map<String, Object> map = new HashMap<>(1);
        long time = System.currentTimeMillis();
        RetryUtil.retry(10, () -> {
            return time;
        }, () -> {
            try {
                log.info("失败次执行");
                Thread.sleep(300);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info(map);
    }

    @Test
    public void retry3() {
        Map<String, Object> map = new HashMap<>(1);
        long time = System.currentTimeMillis();
        RetryUtil.retry(3, () -> {
            map.put("res", s(time));
            return time;
        }, () -> {
            try {
                log.info("失败1次执行");
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, () -> {
            try {
                log.info("失败2次执行");
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info(map);
    }

    private String s(long o) {
        long time = System.currentTimeMillis();
        if (o > time - 1000) {
            throw new RuntimeException("");
        }
        return "" + time;
    }

    @Test
    public void testTime(){
        LocalDateTime time=LocalDateTime.now().plusSeconds(5);
        ThreadUtil.sleep(2000);
        log.info("2秒后比较{}",time.isBefore(LocalDateTime.now()));
        ThreadUtil.sleep(2000);
        log.info("2秒后比较{}",time.isBefore(LocalDateTime.now()));
        ThreadUtil.sleep(2000);
        log.info("2秒后比较{}",time.isBefore(LocalDateTime.now()));
    }
}