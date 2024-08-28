package com.jiao.product.service.impl;

import com.alibaba.fastjson2.JSON;
import com.jiao.model.entity.product.Category;
import com.jiao.product.mapper.CategoryMapper;
import com.jiao.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<Category> findOneCategory() {
        // 从Redis缓存中查询所有的一级分类数据
        String categoryListJson = (String) redisTemplate.opsForValue().get("category:one");
        if (!StringUtils.isEmpty(categoryListJson)) {
            List<Category> categoryList = JSON.parseArray(categoryListJson, Category.class);
            log.info("从Redis缓存中查询到了所有的一级分类数据");
            return categoryList;
        }
        List<Category> categoryList = categoryMapper.findOneCategory();
        log.info("从数据库中查询到了所有的一级分类数据");
        redisTemplate.opsForValue().set("category:one", JSON.toJSONString(categoryList), 7, TimeUnit.DAYS);
        return categoryList;
    }

    @Override
    @Cacheable(value = "category", key = "'all'")
    public List<Category> findCategoryTree() {
        List<Category> categoryList = categoryMapper.findAll();
        //全部一级分类
        List<Category> oneCategoryList = categoryList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);
                if (!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}
