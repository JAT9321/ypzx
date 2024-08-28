package com.jiao.exception;


import com.jiao.model.vo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(null, 201, "出现了异常");
    }

    @ExceptionHandler(SkyException.class)
    @ResponseBody
    public Result error(SkyException e) {
        e.printStackTrace();
        return Result.build(null, e.getResultCodeEnum());
    }

}
