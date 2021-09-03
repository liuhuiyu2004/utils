package com.liuhuiyu.util.bytes;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-03 10:46
 */
public class ByteUtil {
    public static String bytesOut(byte[] bytes) {
        return bytesOut(bytes, " ");
    }

    public static String bytesOut(byte[] bytes, String intervalSymbol) {
        StringBuilder outString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            outString.append(intervalSymbol).append(hex);
        }
        return outString.toString();
    }
}
