package com.nhlshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nhlshop.dto.Page;
import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.ProductEntity;
import com.nhlshop.service.ICategoryService;
import com.nhlshop.service.IProductService;

@RestController
@RequestMapping()
public class ProductController {
        @Autowired
        private IProductService productService;

        @Autowired
        private ICategoryService categoryService;

        @GetMapping("/categories/{id}")
        public ResponseEntity<ResponseObject> getProductByCategory(@PathVariable Long id,
                        @RequestParam("page") int page,
                        @RequestParam("limit") int limit) {
                Pageable pageable = PageRequest.of(page - 1, limit);
                Page<ProductEntity> result = new Page<>();
                result.setPage(page);
                result.setList(productService.findByCategory(categoryService.findById(id).get(), pageable));
                result.setTotalpage((int) Math.ceil((double) (productService.totalItem()) / limit));
                return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "Get products successfully", result));
        }

        @GetMapping("product/{id}")
        public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
                Optional<ProductEntity> product = productService.findById(id);
                return product.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "Find product successfully", product))
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ResponseObject("FAILED", "Cannot find product with id " + id, ""));
        }

        @GetMapping("new-release")
        public ResponseEntity<ResponseObject> findProductNewRelease(@RequestParam("page") int page,
                        @RequestParam("limit") int limit) {
                List<ProductEntity> products = productService.findAllByDesc(page - 1, limit);

                return products.size() > 0 ? ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "get products successfully",
                                                products))
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ResponseObject("FAILED", "Cannot get products", ""));
        }

        @GetMapping("top-seller")
        public ResponseEntity<ResponseObject> getTopSeller(@RequestParam("page") int page,
                        @RequestParam("limit") int limit) {
                List<ProductEntity> products = productService.getTopSeller(page - 1, limit);
                return products.size() > 0 ? ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "get products successfully",
                                                products))
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ResponseObject("FAILED", "Cannot get products", ""));
        }

        @GetMapping("/search")
        public ResponseEntity<ResponseObject> search(@RequestParam("keyword") String keyword) {
                List<ProductEntity> products = new ArrayList<>();
                if (keyword.toLowerCase().trim().length() > 0) {
                        products = productService.search(keyword);
                }
                return products.size() > 0 ? ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "Get products successfully",
                                                products))
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ResponseObject("FAILED", "Cannot get products", ""));
        }
}
