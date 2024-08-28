package com.jiao.service;

import com.github.pagehelper.PageInfo;
import com.jiao.model.dto.system.AssginRoleDto;
import com.jiao.model.dto.system.LoginDto;
import com.jiao.model.dto.system.SysUserDto;
import com.jiao.model.entity.system.SysUser;
import com.jiao.model.vo.system.LoginVo;

public interface SysUserService {

    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, int pageNum, int pageSize);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}
