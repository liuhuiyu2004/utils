package com.liuhuiyu.util.web;

import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-09 11:42
 */
@Log4j2
public class WebUtilTest extends TestCase {
    public void testBtoA() {
        String b = "abcd";
        String a = JsUtil.btoa(b);
        log.info("bToA a={};b={}", a, b);
    }
    public void testAtoB() {
        String a = "YWJjZA==";
        String b = JsUtil.atob(a);
        log.info("aToB a={};b={}", a, b);
    }
}