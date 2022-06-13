package com.nhlshop.service;

import org.springframework.stereotype.Service;

import com.nhlshop.entities.CategoryEntity;

@Service
public interface ICategoryService extends IService<CategoryEntity> {
    CategoryEntity getById(Long id);

    CategoryEntity getByName(String name);
}
