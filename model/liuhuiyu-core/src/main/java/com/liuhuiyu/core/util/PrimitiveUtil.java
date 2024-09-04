package com.liuhuiyu.core.util;

import java.util.Arrays;

/**
 * 原型工具
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/4 9:27
 */
public class PrimitiveUtil {

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Integer[] convertPrimitiveArrayToBoxedArray(int[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        return Arrays.stream(primitiveArray).boxed().toArray(Integer[]::new);
    }

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Double[] convertPrimitiveArrayToBoxedArray(double[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        return Arrays.stream(primitiveArray).boxed().toArray(Double[]::new);
    }

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Long[] convertPrimitiveArrayToBoxedArray(long[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        return Arrays.stream(primitiveArray).boxed().toArray(Long[]::new);
    }

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Boolean[] convertPrimitiveArrayToBoxedArray(boolean[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        Boolean[] res = new Boolean[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            res[i] = primitiveArray[i];
        }
        return res;
    }

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Float[] convertPrimitiveArrayToBoxedArray(float[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        Float[] res = new Float[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            res[i] = primitiveArray[i];
        }
        return res;
    }

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Short[] convertPrimitiveArrayToBoxedArray(short[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        Short[] res = new Short[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            res[i] = primitiveArray[i];
        }
        return res;
    }

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Byte[] convertPrimitiveArrayToBoxedArray(byte[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        Byte[] res = new Byte[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            res[i] = primitiveArray[i];
        }
        return res;
    }

    /**
     * 将原始类型数组转换为其对应的封装类型数组
     *
     * @param primitiveArray 原始类型数组
     * @return 封装类型数组
     */
    public static Character[] convertPrimitiveArrayToBoxedArray(char[] primitiveArray) {
        if (primitiveArray == null) {
            return null;
        }
        Character[] res = new Character[primitiveArray.length];
        for (int i = 0; i < primitiveArray.length; i++) {
            res[i] = primitiveArray[i];
        }
        return res;
    }

    /**
     * 获取原始类型的封装类型
     *
     * @param primitiveType 原始类型的 Class 对象
     * @return 封装类型的 Class 对象
     */
    private static Class<?> getBoxedType(Class<?> primitiveType) {
        if (primitiveType.equals(int.class)) {
            return Integer.class;
        }
        else if (primitiveType.equals(boolean.class)) {
            return Boolean.class;
        }
        else if (primitiveType.equals(double.class)) {
            return Double.class;
        }
        else if (primitiveType.equals(float.class)) {
            return Float.class;
        }
        else if (primitiveType.equals(long.class)) {
            return Long.class;
        }
        else if (primitiveType.equals(short.class)) {
            return Short.class;
        }
        else if (primitiveType.equals(byte.class)) {
            return Byte.class;
        }
        else if (primitiveType.equals(char.class)) {
            return Character.class;
        }
        else {
            throw new IllegalArgumentException("Unsupported primitive type: " + primitiveType);
        }
    }
}
