package com.jiao.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import com.jiao.model.vo.system.ValidateCodeVo;
import com.jiao.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public ValidateCodeVo generateValidateCode() {

        //糊涂工具包生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        String code = captcha.getCode();
        String imageBase64 = captcha.getImageBase64();

        //生成uuid保存到redis中将code
        String codeKey = UUID.randomUUID().toString().replace("-", "");

        //保存到redis中
        redisTemplate.opsForValue().set("user:login:validatecode:" + codeKey, code, 5, TimeUnit.MINUTES);

        //响应对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(codeKey);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }
}
