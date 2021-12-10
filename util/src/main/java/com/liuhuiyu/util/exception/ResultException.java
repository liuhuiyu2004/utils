package com.liuhuiyu.util.exception;

import com.liuhuiyu.util.constant.enums.ResultEnum;
import org.jetbrains.annotations.NotNull;

/**
 * Result解析报错
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-12-10 16:25
 */
public class ResultException extends LhyException{

    public ResultException(String message) {
        super(message);
    }
}
