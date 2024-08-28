package com.jiao.product.service;

import com.github.pagehelper.PageInfo;
import com.jiao.model.dto.h5.ProductSkuDto;
import com.jiao.model.dto.product.SkuSaleDto;
import com.jiao.model.entity.product.ProductSku;
import com.jiao.model.vo.h5.ProductItemVo;

import java.util.List;

// 业务接口
public interface ProductService {

    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}