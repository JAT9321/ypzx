package com.jiao.product.mapper;

import com.jiao.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {

    ProductDetails getByProductId(Long productId);

}