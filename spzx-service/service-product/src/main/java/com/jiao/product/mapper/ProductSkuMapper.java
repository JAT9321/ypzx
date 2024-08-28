package com.jiao.product.mapper;

import com.jiao.model.dto.h5.ProductSkuDto;
import com.jiao.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    List<ProductSku> findProductSkuBySale();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku getById(Long skuId);

    List<ProductSku> findByProductId(Long productId);

    void updateSale(@Param("skuId") Long skuId, @Param("num") Integer num);
}