package com.nhlshop.service;

import com.nhlshop.entities.CategoryEntity;

public interface ICategoryService extends IService<CategoryEntity> {
    CategoryEntity getById(Long id);
}
