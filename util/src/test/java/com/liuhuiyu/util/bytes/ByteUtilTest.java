package com.liuhuiyu.util.bytes;

import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-22 10:06
 */
public class ByteUtilTest {
    @Test
    public void bytesOut() {
        byte[] bytes = {'a', 'b', 'c'};
        String s = ByteUtil.bytesOut(bytes);
//        log.info("s={}", s);
    }
    @Test
    public void binary() {
        byte[] bytes = {'a', 'b', 'c'};
        String s = ByteUtil.binary(bytes, 16);
//        log.info("s={}", s);
    }
    @Test
    public void string2HexUTF8() {
        String hello = ByteUtil.string2HexUTF8("大家好");
//        log.info(hello);
        String hello2=ByteUtil.string2HexUnicode("大家好");
//        log.info(hello2);
    }
}