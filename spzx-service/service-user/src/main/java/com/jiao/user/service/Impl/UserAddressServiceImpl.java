package com.jiao.user.service.Impl;

import com.jiao.model.entity.user.UserAddress;
import com.jiao.user.mapper.UserAddressMapper;
import com.jiao.user.service.UserAddressService;
import com.jiao.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

   @Autowired
   private UserAddressMapper userAddressMapper;

   @Override
   public List<UserAddress> findUserAddressList() {
      Long userId = AuthContextUtil.getUserInfo().getId();
      return userAddressMapper.findByUserId(userId);
   }

    //根据id获取收货地址信息
    @Override
    public UserAddress getUserAddress(Long id) {
        return userAddressMapper.getUserAddress(id);
    }

}