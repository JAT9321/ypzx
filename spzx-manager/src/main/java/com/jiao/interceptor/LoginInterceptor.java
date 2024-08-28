package com.jiao.interceptor;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.jiao.model.entity.system.SysUser;
import com.jiao.model.vo.common.Result;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获得请求方式
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) { // 跨域预检请求，直接放行
            return true;
        }

        //获得token
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }

        // token不为空，检测token的合法性
        String sysUserInfoJson = (String) redisTemplate.opsForValue().get("user:login:" + token);
        if (StrUtil.isEmpty(sysUserInfoJson)) {
            responseNoLoginInfo(response);
            return false;
        }

        // 将用户数据存储到ThreadLocal中
        SysUser sysUser = JSON.parseObject(sysUserInfoJson, SysUser.class);
        AuthContextUtil.set(sysUser);

        // 重置Redis的用户数据的有效时间
        redisTemplate.expire("user:login:" + token, 30, TimeUnit.MINUTES);

        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();
    }


    // 响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
