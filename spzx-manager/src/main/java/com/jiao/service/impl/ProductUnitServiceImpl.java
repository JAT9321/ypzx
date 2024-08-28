package com.jiao.service.impl;


import com.jiao.mapper.ProductUnitMapper;
import com.jiao.model.entity.base.ProductUnit;
import com.jiao.service.ProductUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}
