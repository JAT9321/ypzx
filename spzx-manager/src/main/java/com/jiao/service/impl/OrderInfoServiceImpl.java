package com.jiao.service.impl;


import cn.hutool.core.date.DateUtil;
import com.jiao.mapper.OrderStatisticsMapper;
import com.jiao.model.dto.order.OrderStatisticsDto;
import com.jiao.model.entity.order.OrderStatistics;
import com.jiao.model.vo.order.OrderStatisticsVo;
import com.jiao.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {

        List<OrderStatistics> statisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);

        //日期列表
        List<String> dateList = statisticsList.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd")).toList();

        //统计金额列表
        List<BigDecimal> amountList = statisticsList.stream().map(OrderStatistics::getTotalAmount).toList();


        // 创建OrderStatisticsVo对象封装响应结果数据
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);

        // 返回数据
        return orderStatisticsVo;
    }
}
