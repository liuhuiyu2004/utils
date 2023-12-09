package com.liuhuiyu.core.lang;

import com.liuhuiyu.core.util.Assert;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-04 9:24
 */
public class ByteUtil {

    public static byte[] longToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    public static long bytesToLong(byte[] buffer) {
        Assert.assertTrue(buffer.length <= 8, new IllegalArgumentException("输入数组长度超过8位"));
        long values = 0;
        for (byte b : buffer) {
            values <<= 8;
            values |= (b & 0xff);
        }
        return values;
    }
    public static byte[] intToBytes(int values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 32 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    public static long bytesToInt(byte[] buffer) {
        Assert.assertTrue(buffer.length <= 4, new IllegalArgumentException("输入数组长度超过4位"));
        int values = 0;
        for (byte b : buffer) {
            values <<= 8;
            values |= (b & 0xff);
        }
        return values;
    }
}
