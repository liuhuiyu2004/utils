package com.liuhuiyu.core.lang.id;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-04 16:01
 */
public class NanoId {
    /**
     * 默认随机数生成器，使用{@link SecureRandom}确保健壮性
     */
    private static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();

    /**
     * 默认随机字母表，使用URL安全的Base64字符
     */
    public static final char[] NUMBER_UPPERCASE_LETTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] NUMBER = "0123456789".toCharArray();
    public static final char[] UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] DEFAULT_ALPHABET = NUMBER_UPPERCASE_LETTERS;

    /**
     * 默认长度
     */
    public static final int DEFAULT_SIZE = 16;

    /**
     * 生成伪随机的NanoId字符串，长度为默认的{@link #DEFAULT_SIZE}，使用密码安全的伪随机生成器
     *
     * @return 伪随机的NanoId字符串
     */
    public static String randomNanoId() {
        return randomNanoId(DEFAULT_SIZE);
    }

    /**
     * 生成伪随机的NanoId字符串
     *
     * @param size ID长度
     * @return 伪随机的NanoId字符串
     */
    public static String randomNanoId(int size) {
        return randomNanoId(null, null, size);
    }

    /**
     * 生成伪随机的NanoId字符串
     *
     * @param alphabet 随机字母表
     * @param size     ID长度
     * @return 伪随机的NanoId字符串
     */
    public static String randomNanoId(char[] alphabet, int size) {
        return randomNanoId(null, alphabet, size);
    }

    /**
     * 生成伪随机的NanoId字符串
     *
     * @param random   随机数生成器
     * @param alphabet 随机字母表
     * @param size     ID长度
     * @return 伪随机的NanoId字符串
     */
    public static String randomNanoId(Random random, char[] alphabet, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
        if (random == null) {
            random = DEFAULT_NUMBER_GENERATOR;
        }
        if (alphabet == null) {
            alphabet = DEFAULT_ALPHABET;
        }
        if (alphabet.length == 0 || alphabet.length >= 256) {
            throw new IllegalArgumentException("Alphabet must contain between 1 and 255 symbols.");
        }
        final int mask = (2 << (int) Math.floor(Math.log(alphabet.length - 1) / Math.log(2))) - 1;
        final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);
        final StringBuilder idBuilder = new StringBuilder();
        while (true) {
            final byte[] bytes = new byte[step];
            random.nextBytes(bytes);
            for (int i = 0; i < step; i++) {
                final int alphabetIndex = bytes[i] & mask;
                if (alphabetIndex < alphabet.length) {
                    idBuilder.append(alphabet[alphabetIndex]);
                    if (idBuilder.length() == size) {
                        return idBuilder.toString();
                    }
                }
            }
        }
    }
}
