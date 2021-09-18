package com.liuhuiyu.httpdemo.controller;

import com.liuhuiyu.util.exception.LhyException;
import com.liuhuiyu.util.model.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

/**
 * 异常捕获处理
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2018/12/21 13:23
 */
@ControllerAdvice
@Log4j2
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<?> handle(Exception e) {
        if (e instanceof LhyException) {
            LhyException fileManagementException = (LhyException) e;
            return Result.error(fileManagementException.getMessage());
        } else if (e instanceof MultipartException) {
            log.error("[MultipartException]{}" + e.toString());
            return Result.error("文件过大");
        }else if(e instanceof  RuntimeException){
            return Result.error(e.getMessage());
        } else {
            log.error("[系统异常]{}", e.toString());
            return Result.error("其他错误："+e);
        }
    }
}
