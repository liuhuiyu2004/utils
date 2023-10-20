package com.liuhuiyu.spring.model;

/**
 * Result解析报错
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-12-10 16:25
 */
public class ResultException extends RuntimeException{

    public ResultException(String message) {
        super(message);
    }
}
