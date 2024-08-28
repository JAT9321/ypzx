package com.jiao.mapper;

import com.jiao.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperLogMapper {
    public abstract void insert(SysOperLog sysOperLog);
}