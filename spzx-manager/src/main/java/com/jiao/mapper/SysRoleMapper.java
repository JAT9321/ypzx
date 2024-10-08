package com.jiao.mapper;


import com.jiao.model.dto.system.SysRoleDto;
import com.jiao.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    public abstract List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    List<SysRole> findAllRoles();
}
