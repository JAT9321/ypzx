package com.jiao.product.service;

import com.jiao.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}
