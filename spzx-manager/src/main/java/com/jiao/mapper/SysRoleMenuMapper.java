package com.jiao.mapper;


import com.jiao.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {

    List<Long> findSysRoleMenuByRoleId(Long roleId);

    public abstract void deleteByRoleId(Long roleId);

    public abstract void doAssign(AssginMenuDto assginMenuDto);

    public abstract void updateSysRoleMenuIsHalf(Long menuId);
}
