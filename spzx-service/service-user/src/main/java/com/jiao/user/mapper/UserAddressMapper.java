package com.jiao.user.mapper;

import com.jiao.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {

    List<UserAddress> findByUserId(Long userId);

    UserAddress getUserAddress(Long id);
}