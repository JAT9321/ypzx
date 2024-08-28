package com.jiao.mapper;

import com.jiao.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductUnitMapper {
    public abstract List<ProductUnit> findAll();
}