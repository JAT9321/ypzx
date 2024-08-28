package com.jiao.mapper;

import com.jiao.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    public abstract List<SysMenu> selectAll();

    void insert(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    int countByParentId(Long id);

    void deleteById(Long id);

    List<SysMenu> selectListByUserId(Long userId);

    SysMenu selectById(Long parentId);
}