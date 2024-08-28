package com.jiao.user.service.Impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.jiao.exception.SkyException;
import com.jiao.model.dto.h5.UserLoginDto;
import com.jiao.model.dto.h5.UserRegisterDto;
import com.jiao.model.entity.user.UserInfo;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.model.vo.h5.UserInfoVo;
import com.jiao.user.mapper.UserInfoMapper;
import com.jiao.user.service.UserInfoService;
import com.jiao.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import com.jiao.user.service.UserInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserRegisterDto userRegisterDto) {

        // 获取数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        //校验参数
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)) {
            throw new SkyException(ResultCodeEnum.DATA_ERROR);
        }

        //校验校验验证码
        String codeValueRedis = redisTemplate.opsForValue().get("phone:code:" + username);
        if (!code.equals(codeValueRedis)) {
            throw new SkyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        UserInfo userInfo = userInfoMapper.getByUsername(username);
        if (null != userInfo) {
            throw new SkyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //保存用户信息
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        userInfoMapper.save(userInfo);

        // 删除Redis中的数据
        redisTemplate.delete("phone:code:" + username);

    }

    @Override
    public String login(UserLoginDto userLoginDto) {

        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        //校验参数
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new SkyException(ResultCodeEnum.DATA_ERROR);
        }

        UserInfo userInfo = userInfoMapper.getByUsername(username);
        if (null == userInfo) {
            throw new SkyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5InputPassword.equals(userInfo.getPassword())) {
            throw new SkyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验是否被禁用
        if (userInfo.getStatus() == 0) {
            throw new SkyException(ResultCodeEnum.ACCOUNT_STOP);
        }


        String token = UUID.randomUUID().toString().replaceAll("-", "");
        userInfo.setPassword(null);
        redisTemplate.opsForValue().set("user:spzx:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;

    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        //        String userInfoJSON = (String) redisTemplate.opsForValue().get("user:spzx:" + token);
        //        if (StringUtils.isEmpty(userInfoJSON)) {
        //            throw new SkyException(ResultCodeEnum.LOGIN_AUTH);
        //        }
        //        UserInfo userInfo = JSON.parseObject(userInfoJSON, UserInfo.class);
        //        UserInfoVo userInfoVo = new UserInfoVo();
        //        BeanUtils.copyProperties(userInfo, userInfoVo);
        //        return userInfoVo;
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }
}
