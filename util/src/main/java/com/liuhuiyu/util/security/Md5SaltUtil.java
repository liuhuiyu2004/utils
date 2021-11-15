package com.liuhuiyu.util.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-11-15 14:53
 */
public class Md5SaltUtil {
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    /**
     * 密码总长度=SALT_LENGTH * 2 + 32
     */
    private static final Integer SALT_LENGTH = 16;
    private static final String CHARSET = "UTF-8";
    private static final String MD5_ALGORITHM = "MD5";


    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex hex
     * @return 将16进制字符串转换成字节数组
     */
    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b byte数组
     * @return 16进制字符串
     */
    private static String byteToHexString(byte[] b) {
        StringBuilder hexString = new StringBuilder();
        for (byte value : b) {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 验证口令是否合法
     *
     * @param password     密码
     * @param passwordInDb 验证密码
     * @return 密码是否匹配
     * @throws NoSuchAlgorithmException     异常
     * @throws UnsupportedEncodingException 异常
     */
    public static boolean validPassword(String password, String passwordInDb)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return validPassword(password, passwordInDb, SALT_LENGTH);
    }

    /**
     * 验证口令是否合法
     *
     * @param password     密码
     * @param passwordInDb 验证密码
     * @param saltLength   混淆盐长度
     * @return 密码是否匹配
     * @throws NoSuchAlgorithmException     异常
     * @throws UnsupportedEncodingException 异常
     */
    public static boolean validPassword(String password, String passwordInDb, Integer saltLength)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //将16进制字符串格式口令转换成字节数组
        byte[] pwdInDb = hexStringToByte(passwordInDb);
        //声明盐变量
        byte[] salt = new byte[saltLength];
        //将盐从数据库中保存的口令字节数组中提取出来
        System.arraycopy(pwdInDb, 0, salt, 0, saltLength);
        //创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(password.getBytes(CHARSET));
        //生成输入口令的消息摘要
        byte[] digest = md.digest();
        //声明一个保存数据库中口令消息摘要的变量
        byte[] digestInDb = new byte[pwdInDb.length - saltLength];
        //取得数据库中口令的消息摘要
        System.arraycopy(pwdInDb, saltLength, digestInDb, 0, digestInDb.length);
        //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
        return Arrays.equals(digest, digestInDb);
    }

    /**
     * 获得加密后的16进制形式口令
     *
     * @param password 密码
     * @return 加密后的口令
     * @throws NoSuchAlgorithmException     异常
     * @throws UnsupportedEncodingException 异常
     */
    public static String getEncryptedPwd(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getEncryptedPwd(password, SALT_LENGTH);
    }

    /**
     * 功能描述
     *
     * @param password   密码
     * @param saltLength 混淆盐长度
     * @return java.lang.String 加密后的密码
     * @author LiuHuiYu
     * Created DateTime 2021-11-15 15:31
     */
    public static String getEncryptedPwd(String password, Integer saltLength)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //声明加密后的口令数组变量
        byte[] pwd;// = null;
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量   12
        byte[] salt = new byte[saltLength];
        //将随机数放入盐变量中
        random.nextBytes(salt);

        //声明消息摘要对象
        MessageDigest md;// = null;
        //创建消息摘要
        md = MessageDigest.getInstance(MD5_ALGORITHM);
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(password.getBytes(CHARSET));
        //获得消息摘要的字节数组
        byte[] digest = md.digest();

        //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + saltLength];
        //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, saltLength);
        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, saltLength, digest.length);
//        for(int i=0;i<pwd.length;i++){
//            System.out.print(pwd[i]);
//        }
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(pwd);
    }
}
