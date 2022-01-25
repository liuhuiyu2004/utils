package com.liuhuiyu.util.asserts;

import com.liuhuiyu.util.constant.enums.ResultEnum;
import com.liuhuiyu.util.exception.LhyException;

import java.lang.reflect.Proxy;
import java.util.EventListener;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-09-10 8:41
 */
public class LhyAssert {
    static Function<String, RuntimeException> exceptionProxy;

    private static RuntimeException throwEx(String message) {
        if (exceptionProxy == null) {
            return new RuntimeException(message);
        }
        else {
            return exceptionProxy.apply(message);
        }
    }

    /**
     * 接受到异常信息后的异常抛出处理
     *
     * @param proxy 接受到异常信息后的代理。
     * @author LiuHuiYu
     * Created DateTime 2021-12-10 14:43
     */
    public static void exceptionProxy(Function<String, RuntimeException> proxy) {
        exceptionProxy = proxy;
    }
    //region assertTrue

    public static void assertTrue(boolean value, RuntimeException exception) {
        if (!value) {
            throw exception;
        }
    }

    public static void assertTrue(boolean value, String message) {
        assertTrue(value, throwEx(message));
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
    //endregion

    //region 枚举模式拦截

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

    /**
     * 对象为空则抛出异常
     *
     * @param object     对象
     * @param resultEnum 错误枚举
     */
    public static void assertNotNull(Object object, ResultEnum resultEnum) {
        assertTrue(object != null, resultEnum);
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


    public static void assertLen(String value, int min, int max, String message) {
        assertNotNull(value, message + "未设定");
        assertTrue(value.length()>=min && value.length()<=max,message+"(长度范围["+min+"-"+max+"])");
    }
    public static void assertLen(String value, int min, int max, RuntimeException exception) {
        assertNotNull(value,exception);
        assertTrue(value.length()>=min && value.length()<=max,exception);
    }
}
