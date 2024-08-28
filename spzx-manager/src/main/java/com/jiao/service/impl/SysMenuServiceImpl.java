package com.jiao.service.impl;

import com.jiao.exception.SkyException;
import com.jiao.mapper.SysMenuMapper;
import com.jiao.mapper.SysRoleMenuMapper;
import com.jiao.model.entity.system.SysMenu;
import com.jiao.model.entity.system.SysUser;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.model.vo.system.SysMenuVo;
import com.jiao.service.SysMenuService;
import com.jiao.utils.AuthContextUtil;
import com.jiao.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList = sysMenuMapper.selectAll();
        if (CollectionUtils.isEmpty(sysMenuList)) return null;
        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList); //构建树形数据
        return treeList;
    }

    @Override
    public void save(SysMenu sysMenu) {
        // 添加新的节点
        sysMenuMapper.insert(sysMenu);

        // 新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenuIsHalf(sysMenu);
    }


    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {
        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectById(sysMenu.getParentId());
        if (parentMenu != null) {
            // 将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            // 递归调用
            updateSysRoleMenuIsHalf(parentMenu);
        }
    }

    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        int count = sysMenuMapper.countByParentId(id);  // 先查询是否存在子菜单，如果存在不允许进行删除
        if (count > 0) {
            throw new SkyException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.deleteById(id);        // 不存在子菜单直接删除
    }

    @Override
    public List<SysMenuVo> findUserMenuList() {

        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();          // 获取当前登录用户的id

        List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(userId);
        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        return this.buildMenus(sysMenuTreeList);

    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }

}