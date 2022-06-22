package com.nhlshop.service.impl;

import java.util.Collection;
import java.util.Optional;

import com.nhlshop.entities.CategoryEntity;
import com.nhlshop.repository.CategoryRepository;
import com.nhlshop.service.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Collection<CategoryEntity> findAll() {
        return (Collection<CategoryEntity>) categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public CategoryEntity saveOrUpdate(CategoryEntity category) {
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}
