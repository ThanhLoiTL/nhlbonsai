package com.nhlshop.converter;

import com.nhlshop.dto.ProductDTO;
import com.nhlshop.entities.ProductEntity;

import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ProductEntity toEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setInformation(productDTO.getInformation());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setQuantitySold(productDTO.getQuantitySold());
        productEntity.setQuantityStock(productDTO.getQuantityStock());
        return productEntity;
    }
}
