package com.liuhuiyu.jpa.util;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-08-11 10:27
 */
public class DataBaseException  extends RuntimeException {

    public DataBaseException() {
    }

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }

    public DataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}