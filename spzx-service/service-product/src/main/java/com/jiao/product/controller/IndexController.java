package com.jiao.product.controller;

import com.jiao.model.entity.product.Category;
import com.jiao.model.entity.product.ProductSku;
import com.jiao.model.vo.common.Result;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.model.vo.h5.IndexVo;
import com.jiao.product.service.CategoryService;
import com.jiao.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product/index")
//@CrossOrigin getway网关全局跨域处理
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result<IndexVo> findData() {
        List<Category> categoryList = categoryService.findOneCategory();
        List<ProductSku> productSkuList = productService.findProductSkuBySale();
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }

}