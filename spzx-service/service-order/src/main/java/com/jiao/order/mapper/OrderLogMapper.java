package com.jiao.order.mapper;

import com.jiao.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper {
    void save(OrderLog orderLog);
}