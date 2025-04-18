package com.liuhuiyu.demo.controller;

import com.liuhuiyu.web.help.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能<p>
 * Created on 2025/4/18 20:54
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@ControllerAdvice
public class ExceptionController {
    private static final Logger LOG = LogManager.getLogger(ExceptionController.class);
    public static final int ERROR_CODE_REG = -10;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<?> handle(Exception e) {
        if (e instanceof MultipartException) {
            LOG.error("[MultipartException]", e);
            return Result.error("文件过大");
        }
        else if (e instanceof RuntimeException) {
            RuntimeException runtimeException = (RuntimeException) e;
            LOG.error("[RuntimeException错误]", e);
            return Result.error(runtimeException.getMessage());
        }
        else {
            LOG.error("[系统异常]", e);
            return Result.error("未知错误：" + e);
        }
    }

    @ResponseBody
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Result<?> handleBindException(BindException exception, HttpServletRequest request) {
        List<FieldError> errorList = exception.getBindingResult().getFieldErrors();
        Map<String, String> dataMap = new HashMap<>(1);
        for (FieldError fieldError : errorList) {
            dataMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        Result<Map<String, String>> result = Result.error("参数错误", -1);
        result.setData(dataMap);
        return result;
    }

    public String getCause(Throwable ex) {
        if (ex.getCause() != null) {
            return ex.getMessage() + "->" + getCause(ex.getCause());
        }
        else {
            return ex.getMessage();
        }
    }
}
