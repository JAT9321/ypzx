package com.jiao.utils;

import com.jiao.model.entity.system.SysUser;
import com.jiao.model.entity.user.UserInfo;

public class AuthContextUtil {

    //创建一个threadlocal对象
    // 后台管理用户
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<SysUser>();
    //商城用户
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<UserInfo>();

    //定义存储数据的静态方法
    public static void set(SysUser user) {
        threadLocal.set(user);
    }

    //获取数据
    public static SysUser get() {
        return threadLocal.get();
    }

    //删除数据
    public static void remove() {
        threadLocal.remove();
    }


    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }


}
