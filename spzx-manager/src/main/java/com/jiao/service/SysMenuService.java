package com.jiao.service;

import com.jiao.model.entity.system.SysMenu;
import com.jiao.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {


    public List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);

    List<SysMenuVo> findUserMenuList();
}
