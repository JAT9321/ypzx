package com.jiao.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiao.mapper.ProductSpecMapper;
import com.jiao.model.entity.product.ProductSpec;
import com.jiao.model.vo.common.Result;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.service.ProductSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {


    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> productSpecList = productSpecMapper.findByPage();
        return new PageInfo<>(productSpecList);
    }

    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }


}
