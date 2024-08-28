package com.jiao.service;

import com.github.pagehelper.PageInfo;
import com.jiao.model.dto.system.SysRoleDto;
import com.jiao.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    Map<String, Object> findAllRoles(Long userId);
}
