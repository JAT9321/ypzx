package com.jiao.product.mapper;

import com.jiao.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {

    List<Brand> findAll();

}