package com.nhlshop.repository;

import com.nhlshop.entities.CategoryEntity;
import com.nhlshop.entities.ProductEntity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory(CategoryEntity category, Pageable pageable);
}
