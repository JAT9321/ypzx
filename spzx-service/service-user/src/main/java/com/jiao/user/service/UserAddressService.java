package com.jiao.user.service;

import com.jiao.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {

    List<UserAddress> findUserAddressList();

    UserAddress getUserAddress(Long id);
}