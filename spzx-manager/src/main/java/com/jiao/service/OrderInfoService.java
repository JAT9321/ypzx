package com.jiao.service;

import com.jiao.model.dto.order.OrderStatisticsDto;
import com.jiao.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
