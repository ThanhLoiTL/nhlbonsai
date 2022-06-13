package com.nhlshop.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhlshop.entities.CategoryEntity;
import com.nhlshop.entities.ProductEntity;

@Service
public interface IProductService extends IService<ProductEntity>, IPageService<ProductEntity> {
    List<ProductEntity> findByCategory(CategoryEntity category, Pageable pageable);
}
