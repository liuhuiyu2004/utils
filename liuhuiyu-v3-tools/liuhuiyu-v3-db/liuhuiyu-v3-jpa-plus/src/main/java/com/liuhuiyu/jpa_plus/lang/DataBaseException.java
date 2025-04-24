package com.liuhuiyu.jpa_plus.lang;

/**
 * 功能<p>
 * Created on 2025/4/24 20:30
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class DataBaseException extends RuntimeException {

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