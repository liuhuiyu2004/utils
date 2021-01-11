package com.liuhuiyu.util.exception;

import com.liuhuiyu.util.constant.enums.ResultEnum;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义报错类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-06-23 13:27
 */
public class LhyException extends RuntimeException {
    private Integer errId;
/*
    public ArchivesManagementException(Integer code, String message) {
        super(message);
        this.code = code;
    }
*/

    public LhyException(@NotNull ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.errId = resultEnum.getCode();
    }

    public LhyException(@NotNull ResultEnum resultEnum, String message) {
        super(resultEnum.getMessage() + ":" + message);
        this.errId = resultEnum.getCode();
    }

    public LhyException(String message, @NotNull ResultEnum resultEnum) {
        super(message + resultEnum.getMessage());
        this.errId = resultEnum.getCode();
    }

    public LhyException(String message, @NotNull ResultEnum resultEnum, String message1) {
        super(message + resultEnum.getMessage() + message1);
        this.errId = resultEnum.getCode();
    }

    public LhyException(String message) {
        super(message);
        this.errId = 0;
    }

    public Integer getErrId() {
        return errId;
    }

    public void setErrId(Integer errId) {
        this.errId = errId;
    }
}
