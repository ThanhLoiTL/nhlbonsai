package com.nhlshop.controller.admin;

import java.util.Collection;
import java.util.Optional;

import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.CategoryEntity;
import com.nhlshop.service.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/category")
public class CategoryAPI {
        @Autowired
        private ICategoryService categoryService;

        @GetMapping
        public ResponseEntity<Collection<CategoryEntity>> getAll() {
                return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
                Optional<CategoryEntity> category = categoryService.findById(id);
                return category.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "Find category successfully", category))
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ResponseObject("FAILED", "Cannot find category with id " + id, ""));
        }

        @PostMapping
        public ResponseEntity<ResponseObject> saveCategory(@RequestBody CategoryEntity categoty) {
                CategoryEntity categoryEntity = categoryService.saveOrUpdate(categoty);
                return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "Insert category successfully", categoryEntity));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseObject> updateCategory(@RequestBody CategoryEntity categoryEntity,
                        @PathVariable Long id) {
                Optional<CategoryEntity> categoryExist = categoryService.findById(id)
                                .map(category -> {
                                        category.setName(categoryEntity.getName());
                                        category.setDescription(categoryEntity.getDescription());
                                        return categoryService.saveOrUpdate(category);
                                });
                return categoryExist.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("OK", "Update category successfully",
                                                categoryExist))
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ResponseObject("FAILED", "Cannot find category with id " + id, ""));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) {
                Optional<CategoryEntity> categoryExist = categoryService.findById(id);
                if (categoryExist.isPresent()) {
                        categoryService.deleteById(id);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        new ResponseObject("OK", "Delete category successfully", ""));
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                new ResponseObject("FAILED", "Cannot find category with id " + id, ""));
        }
}
