package com.liuhuiyu.util.web;


/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-09 11:42
 */
public class WebUtilTest {
    public void testBtoA() {
        String b = "abcd";
        String a = JsUtil.btoa(b);
        //log.info("bToA a={};b={}", a, b);
    }
    public void testAtoB() {
        String a = "YWJjZA==";
        String b = JsUtil.atob(a);
        //log.info("aToB a={};b={}", a, b);
    }
}