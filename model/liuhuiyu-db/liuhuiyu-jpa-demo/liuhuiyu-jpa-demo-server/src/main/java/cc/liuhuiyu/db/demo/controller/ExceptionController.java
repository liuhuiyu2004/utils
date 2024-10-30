package cc.liuhuiyu.db.demo.controller;

import com.liuhuiyu.dto.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常捕获处理
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2018/12/21 13:23
 */
@ControllerAdvice
public class ExceptionController {
    public static final Logger LOG = LogManager.getLogger(ExceptionController.class);
    /**
     * 注册异常代码 -10
     * Created DateTime 2021-07-05 15:11
     */
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

    public String getCause(Throwable ex) {
        if (ex.getCause() != null) {
            return ex.getMessage() + "->" + getCause(ex.getCause());
        }
        else {
            return ex.getMessage();
        }
    }

    /**
     * 参数校验异常处理
     *
     * @param exception
     * @return
     */
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

}
