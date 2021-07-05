package com.liuhuiyu.util.asserts;

import com.liuhuiyu.util.constant.enums.ResultEnum;
import com.liuhuiyu.util.exception.LhyException;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-09-10 8:41
 */
public class LhyAssert {
    //region assertTrue

    public static void assertTrue(boolean value, RuntimeException exception) {
        if (!value) {
            throw exception;
        }
    }

    public static void assertTrue(boolean value, String message) {
        assertTrue(value, new RuntimeException(message));
    }

    /**
     * 对象不为空则抛出异常
     *
     * @param value      对象
     * @param resultEnum 错误枚举
     */
    public static void assertTrue(boolean value, ResultEnum resultEnum) {
        if (!value) {
            throw new LhyException(resultEnum);
        }
    }
    //endregion

    //region assertFalse

    public static void assertFalse(boolean value, String message) {
        assertTrue(!value, message);
    }

    public static void assertFalse(boolean value, RuntimeException exception) {
        assertTrue(!value, exception);
    }
    //endregion

    //region assertNotNull
    public static void assertNotNull(Object object, RuntimeException exception) {
        assertTrue(object != null, exception);
    }

    /**
     * 对象为空则抛出异常
     *
     * @param object 对象
     */
    public static void assertNotNull(Object object, String message) {
        assertTrue(object != null, message);
    }

    /**
     * 对象为空则抛出异常
     *
     * @param object     对象
     * @param resultEnum 错误枚举
     */
    public static void assertNotNull(Object object, ResultEnum resultEnum) {
        assertTrue(object != null, resultEnum);
    }
    //endregion

    //region assertNull
    /**
     * 对象不为空则抛出异常
     *
     * @param object 对象
     */
    public static void assertNull(Object object, RuntimeException exception) {
        assertTrue(object == null, exception);
    }

    /**
     * 对象不为空则抛出异常
     *
     * @param object 对象
     */
    public static void assertNull(Object object, String message) {
        assertTrue(object == null, message);
    }

    /**
     * 对象不为空则抛出异常
     *
     * @param object     对象
     * @param resultEnum 错误枚举
     */
    public static void assertNull(Object object, ResultEnum resultEnum) {
        assertTrue(object == null, resultEnum);
    }
    //endregion
}
