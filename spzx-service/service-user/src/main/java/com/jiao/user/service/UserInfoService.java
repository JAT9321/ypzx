package com.jiao.user.service;

import com.jiao.model.dto.h5.UserLoginDto;
import com.jiao.model.dto.h5.UserRegisterDto;
import com.jiao.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
