package com.liuhuiyu.util;

import lombok.extern.log4j.Log4j2;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-12-29 9:16
 */
public class HttpUtilTest {

//    private volatile boolean s;
//    private volatile String b(){
//
//    }
//    @Contended
//    class b{
////LoadLoad;
////StoreStore;
//        void s2(){
//            ----StoreStoreBarrier----
//                    Load;
//            Store;
//        }
////        StoreLoad;
//    }

    public void testStringToInt() {
        String s = "123s";
        Integer i = Integer.getInteger(s, 101);
//        log.info("i = {}", i);
    }
}