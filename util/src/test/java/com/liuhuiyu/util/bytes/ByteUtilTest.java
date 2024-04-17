package com.liuhuiyu.util.bytes;

import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        String hello2 = ByteUtil.string2HexUnicode("大家好");
//        log.info(hello2);
    }

    public void soft() {
        SoftReference<byte[]> sr = new SoftReference<>(new byte[100]);
        SoftReference<String[]> sr2 = new SoftReference<>(new String[100]);
    }
    @Test
    public void date(){
        Date date = new Date();
        Timestamp date1 = new Timestamp(date.getTime());
    }
    @Test
    public void testStream(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        final int sum = list.stream().map(Integer::valueOf).mapToInt(Integer::intValue).sum();
        list.add("8");
        list.add("9");
        final int sum2 = list.stream().map(Integer::valueOf).mapToInt(Integer::intValue).sum();
        list.add("10");
        final int sum3 = list.stream().map(Integer::valueOf).mapToInt(Integer::intValue).sum();
    }
}