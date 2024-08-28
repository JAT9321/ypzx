package com.jiao.pay.mapper;

import com.jiao.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {
    void save(PaymentInfo paymentInfo);
    PaymentInfo getByOrderNo(String orderNo);

    void updateById(PaymentInfo paymentInfo);
}