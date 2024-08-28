package com.jiao.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiao.mapper.SysRoleMapper;
import com.jiao.mapper.SysRoleUserMapper;
import com.jiao.model.dto.system.SysRoleDto;
import com.jiao.model.entity.system.SysRole;
import com.jiao.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        //查询所有角色
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();
        //查询本个用户拥有的角色
        List<Long> sysRoles = sysRoleUserMapper.findSysUserRoleByUserId(userId);
        // 构建响应结果数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("allRolesList", sysRoleList);
        resultMap.put("sysUserRoles", sysRoles);
        return resultMap;
    }
}
