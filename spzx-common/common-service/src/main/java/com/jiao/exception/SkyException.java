package com.jiao.exception;


import com.jiao.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class SkyException extends RuntimeException {

    private int code;
    private String message;
    private ResultCodeEnum resultCodeEnum; // 封装错误状态码和错误消息

    public SkyException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public SkyException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
