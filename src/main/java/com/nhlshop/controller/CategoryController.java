package com.nhlshop.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.CategoryEntity;
import com.nhlshop.service.ICategoryService;

@RestController
@RequestMapping()
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<ResponseObject> getAll() {
        Collection<CategoryEntity> listCategory = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Get categories successfully", listCategory));
    }
}
