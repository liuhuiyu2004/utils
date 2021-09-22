package com.liuhuiyu.util.bytes;

import java.math.BigInteger;

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


    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param b 字节数组
     * @return 16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (byte value : b) {
            result.append(String.format("%02X", value));
        }
        return result.toString();
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param src 16进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * 字符UTF8串转16进制字符串
     *
     * @param strPart 字符串
     * @return 16进制字符串
     */
    public static String string2HexUTF8(String strPart) {

        return string2HexString(strPart, "UTF-8");
    }

    /**
     * 字符Unicode串转16进制字符串
     *
     * @param strPart 字符串
     * @return 16进制字符串
     */
    public static String string2HexUnicode(String strPart) {

        return string2HexString(strPart, "Unicode");
    }

    /**
     * 字符GBK串转16进制字符串
     *
     * @param strPart 字符串
     * @return 16进制字符串
     */
    public static String string2HexGBK(String strPart) {

        return string2HexString(strPart, "GBK");
    }

    /**
     * 字符串转16进制字符串
     *
     * @param strPart    字符串
     * @param tochartype hex目标编码
     * @return 16进制字符串
     */
    public static String string2HexString(String strPart, String tochartype) {
        try {
            return bytes2HexString(strPart.getBytes(tochartype));
        }
        catch (Exception e) {
            return "";
        }
    }

    /**
     * 16进制UTF-8字符串转字符串
     *
     * @param src 16进制字符串
     * @return 字节数组
     */
    public static String hexUTF82String(String src) {
        return hexString2String(src, "UTF-8", "UTF-8");
    }

    /**
     * 16进制GBK字符串转字符串
     *
     * @param src 16进制字符串
     * @return 字节数组
     */
    public static String hexGBK2String(String src) {

        return hexString2String(src, "GBK", "UTF-8");
    }

    /**
     * 16进制Unicode字符串转字符串
     *
     * @param src 16进制字符串
     * @return 字节数组
     */
    public static String hexUnicode2String(String src) {
        return hexString2String(src, "Unicode", "UTF-8");
    }

    /**
     * 16进制字符串转字符串
     *
     * @param src 16进制字符串
     * @return 字节数组
     */
    public static String hexString2String(String src, String oldchartype, String chartype) {
        byte[] bts = hexString2Bytes(src);
        try {
            return oldchartype.equals(chartype) ?
                    new String(bts, oldchartype) :
                    new String(new String(bts, oldchartype).getBytes(), chartype);
        }
        catch (Exception e) {

            return "";
        }
    }

    /**
     * int转16进制字符串,使用1字节表示16进制字符串
     *
     * @param src int
     * @return String
     */
    public static String numToHex8(int src) {
        //2表示需要两个16进制数
        return String.format("%02x", src);
    }

    /**
     * int转16进制字符串,使用2字节表示16进制字符串
     *
     * @param src int
     * @return String
     */
    public static String numToHex16(int src) {
        //4表示需要两个16进制数
        return String.format("%04x", src);
    }

    /**
     * 将一个int数字转换为二进制的字符串形式。
     * @param src   int 需要转换的int类型数据
     * @param digit int 要转换的二进制位数，位数不足则在前面补0
     * @return String 二进制的字符串形式
     */
    public static String binary2decimal(int src, int digit) {
        StringBuilder binStr = new StringBuilder();
        for (int i = digit - 1; i >= 0; i--) {
            binStr.append((src >> i) & 1);
        }
        return binStr.toString();
    }

    /**
     * 将一个二进制字符串转十六进制字符串形式。
     * @param src String 需要转换的二进制字符串 比如：000000111111 转换成16进制为 0F  注意长度不是8的倍数高位要补全0
     * @return String 二进制的字符串形式
     */
    public static String bin2hex(String src) {
        StringBuilder sb = new StringBuilder();
        int len = src.length();
        //System.out.println("原数据长度：" + (len / 8) + "字节");
        for (int i = 0; i < len / 4; i++) {
            //每4个二进制位转换为1个十六进制位
            String temp = src.substring(i * 4, (i + 1) * 4);
            int tempInt = Integer.parseInt(temp, 2);
            String tempHex = Integer.toHexString(tempInt).toUpperCase();
            sb.append(tempHex);
        }
        return sb.toString();
    }
}
