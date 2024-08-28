package com.jiao.controller;


import com.jiao.log.annotaion.Log;
import com.jiao.model.dto.system.LoginDto;
import com.jiao.model.entity.system.SysUser;
import com.jiao.model.vo.common.Result;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.model.vo.h5.UserInfoVo;
import com.jiao.model.vo.system.LoginVo;
import com.jiao.model.vo.system.SysMenuVo;
import com.jiao.model.vo.system.ValidateCodeVo;
import com.jiao.service.SysMenuService;
import com.jiao.service.SysUserService;
import com.jiao.service.ValidateCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }



    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    //获取用户信息，使用token从redis中
    @Operation(summary = "获得用户信息")
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
        SysUser sysUser = sysUserService.getUserInfo(token);
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList = sysMenuService.findUserMenuList();
        return Result.build(sysMenuVoList, ResultCodeEnum.SUCCESS);
    }
}
