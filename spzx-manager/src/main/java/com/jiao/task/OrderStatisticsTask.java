package com.jiao.task;


import cn.hutool.core.date.DateUtil;
import com.jiao.mapper.OrderInfoMapper;
import com.jiao.mapper.OrderStatisticsMapper;
import com.jiao.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Scheduled(cron = "0 0 2 * * ?") // 每天的凌晨两点执行
    public void orderTotalAmountStatistics() {
        // 查询出前一天的销售数据，保存到统计表中
        String createTime = DateUtil.offsetDay(new Date(), -1).toString(new SimpleDateFormat("yyyy-MM-dd"));
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        if (orderStatistics != null) {
            orderStatisticsMapper.insert(orderStatistics);
        }
    }

}
