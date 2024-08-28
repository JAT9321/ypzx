package com.jiao.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiao.exception.SkyException;
import com.jiao.mapper.SysRoleUserMapper;
import com.jiao.mapper.SysUserMapper;
import com.jiao.model.dto.system.AssginRoleDto;
import com.jiao.model.dto.system.LoginDto;
import com.jiao.model.dto.system.SysUserDto;
import com.jiao.model.entity.system.SysUser;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.model.vo.system.LoginVo;
import com.jiao.service.SysUserService;
import com.jiao.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;


    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {

        // 检验验证码
        String captcha = loginDto.getCaptcha(); //用户输入的验证码code
        String codeKey = loginDto.getCodeKey(); //存在redis中对于code值的key
        // redis中的验证码code
        String redisCode = (String) redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode, captcha)) {
            throw new SkyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //验证通过，删除redis中的验证码
        redisTemplate.delete("user:login:validatecode:" + codeKey);

        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if (sysUser == null) {
            throw new SkyException(ResultCodeEnum.LOGIN_ERROR);
        }

        String loginDtoPassword = loginDto.getPassword();
        String md5LoginPassword = DigestUtils.md5DigestAsHex(loginDtoPassword.getBytes());
        if (!md5LoginPassword.equals(sysUser.getPassword())) {
            throw new SkyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //生成令牌作为key，把用户信息保存到redis中
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token, JSON.toJSONString(sysUser), 30, TimeUnit.MINUTES);

        //响应对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
//        String userJson = (String) redisTemplate.opsForValue().get("user:login:" + token);
//        return JSON.parseObject(userJson, SysUser.class);
        // 登录拦截器校验成功后，会把用户信息保存到threadlocal中，直接取出即可。
        return AuthContextUtil.get();
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserList);
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {

        // 先查询是否有相同的用户
        SysUser sysUserFromDb = sysUserMapper.finByUserName(sysUser.getUserName());
        if (sysUserFromDb != null) {
            throw new SkyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //对密码进行加密
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5DigestAsHex);
        sysUser.setStatus(0);

        sysUserMapper.saveSysUser(sysUser);


    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {

        // 删除之前的所有的用户所对应的角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());
        // 分配新的角色数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        roleIdList.stream().forEach(roleId -> {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(), roleId);
        });

    }
}
