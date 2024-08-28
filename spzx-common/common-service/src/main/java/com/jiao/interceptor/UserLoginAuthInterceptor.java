package com.jiao.interceptor;

import com.alibaba.fastjson2.JSON;
import com.jiao.model.entity.user.UserInfo;
import com.jiao.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, Object handler) throws Exception {
        String token = redisTemplate.opsForValue().get("user:spzx:" + request.getHeader("token"));
        AuthContextUtil.setUserInfo(JSON.parseObject(token, UserInfo.class));
        return true;
    }
}
