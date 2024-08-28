package com.jiao.product.mapper;

import com.jiao.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    Product getById(Long id);
}