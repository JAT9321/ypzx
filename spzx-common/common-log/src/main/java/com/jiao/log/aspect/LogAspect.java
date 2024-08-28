package com.jiao.log.aspect;


import com.jiao.log.annotaion.Log;
import com.jiao.log.service.AsyncOperLogService;
import com.jiao.log.uiils.LogUtil;
import com.jiao.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {            // 环绕通知切面类定义

    @Autowired
    private AsyncOperLogService asyncOperLogService;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {

        // 构建前置参数
        SysOperLog sysOperLog = new SysOperLog();

        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);

        Object proceed = null;
        try {
            // 执行业务代码
            proceed = joinPoint.proceed();
            // 执行业务方法之后
            LogUtil.afterHandlLog(sysLog, proceed, sysOperLog, 0, null);
            // 构建响应结果参数
        } catch (Throwable e) {                                 // 代码执行进入到catch中，
            // 业务方法执行产生异常
            e.printStackTrace();                                // 打印异常信息
            LogUtil.afterHandlLog(sysLog, proceed, sysOperLog, 1, e.getMessage());
            throw new RuntimeException();
        }

        // 保存日志数据
        asyncOperLogService.saveSysOperLog(sysOperLog);

        // 返回执行结果
        return proceed;
    }
}