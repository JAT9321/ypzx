package com.jiao.mapper;


import com.jiao.model.dto.system.SysUserDto;
import com.jiao.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    /**
     * 根据用户名查询用户数据
     *
     * @param userName
     * @return
     */
    public SysUser selectByUserName(String userName);

    List<SysUser> findByPage(SysUserDto sysUserDto);

    SysUser finByUserName(String userName);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);
}
