package com.jiao.log.service;

import com.jiao.model.entity.system.SysOperLog;

public interface AsyncOperLogService {

    public abstract void saveSysOperLog(SysOperLog sysOperLog);

}
