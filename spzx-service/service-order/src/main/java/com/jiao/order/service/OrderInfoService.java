package com.jiao.order.service;

import com.github.pagehelper.PageInfo;
import com.jiao.model.dto.h5.OrderInfoDto;
import com.jiao.model.entity.order.OrderInfo;
import com.jiao.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);

    OrderInfo getByOrderNo(String orderNo) ;

    void updateOrderStatus(String orderNo, Integer orderStatus);
}
