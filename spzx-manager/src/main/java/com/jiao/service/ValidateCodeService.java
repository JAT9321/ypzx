package com.jiao.service;

import com.jiao.model.vo.system.ValidateCodeVo;

public interface ValidateCodeService {


    //生成验证码
    ValidateCodeVo generateValidateCode();
}
