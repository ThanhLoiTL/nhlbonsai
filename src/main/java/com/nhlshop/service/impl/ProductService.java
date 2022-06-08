package com.nhlshop.service.impl;

import java.util.Collection;
import java.util.Optional;

import com.nhlshop.entities.ProductEntity;
import com.nhlshop.repository.ProductRepository;
import com.nhlshop.service.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Collection<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductEntity saveOrUpdate(ProductEntity product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Collection<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public int totalItem() {
        return (int) productRepository.count();
    }

}
