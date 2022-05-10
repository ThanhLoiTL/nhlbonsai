package com.nhlshop.controller.admin;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhlshop.converter.ProductConverter;
import com.nhlshop.dto.ProductDTO;
import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.ProductEntity;
import com.nhlshop.service.ICategoryService;
import com.nhlshop.service.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<Collection<ProductEntity>> getAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<ProductEntity> product = productService.findById(id);
        return product.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Find product successfully", product))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Cannot find product with id " + id, ""));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> saveProduct(@RequestParam("product") String product,
            @RequestParam("file") MultipartFile multipartFile) {
        ProductDTO productDTO = new ProductDTO();
        try {
            productDTO = new ObjectMapper().readValue(product, ProductDTO.class);
        } catch (JsonMappingException e1) {
            e1.printStackTrace();
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        ProductEntity productEntity = productConverter.toEntity(productDTO);
        productEntity.setCategory(categoryService.getById(productDTO.getCategory()));
        try {
            productEntity.setImage(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productEntity = productService.saveOrUpdate(productEntity);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Insert product successfully", productEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@RequestParam("product") String product,
            @RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) {
        try {
            ProductDTO productDTO = new ObjectMapper().readValue(product, ProductDTO.class);
            Optional<ProductEntity> productExist = productService.findById(id)
                    .map(p -> {
                        p.setName(productDTO.getName());
                        p.setInformation(productDTO.getInformation());
                        p.setDescription(productDTO.getDescription());
                        p.setPrice(productDTO.getPrice());
                        p.setQuantitySold(productDTO.getQuantitySold());
                        p.setQuantityStock(productDTO.getQuantityStock());
                        try {
                            p.setImage(multipartFile.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        p.setCategory(categoryService.getById(productDTO.getCategory()));
                        return productService.saveOrUpdate(p);
                    });
            return productExist.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Update category successfully", productExist))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ResponseObject("FAILED", "Cannot find category with id " + id, ""));
        } catch (JsonMappingException e1) {
            e1.printStackTrace();
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("FAILED", "Cannot IMPLEMENTED", ""));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        Optional<ProductEntity> productExist = productService.findById(id);
        if (productExist.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete product successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("FAILED", "Cannot find product with id " + id, ""));
    }
}
