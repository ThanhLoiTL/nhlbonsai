package com.nhlshop.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.nhlshop.entities.CategoryEntity;
import com.nhlshop.entities.ProductEntity;
import com.nhlshop.repository.ProductRepository;
import com.nhlshop.service.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<ProductEntity> findByCategory(CategoryEntity category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    @Override
    public List<ProductEntity> findAllByDesc(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    @Override
    public List<ProductEntity> getTopSeller(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "quantitySold")))
                .getContent();
    }

}
