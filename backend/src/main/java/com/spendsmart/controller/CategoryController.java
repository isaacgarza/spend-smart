package com.spendsmart.controller;

import com.spendsmart.cache.Cache;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.CategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    private final Cache cache;

    @Autowired
    public CategoryController(Cache cache) {
        this.cache = cache;
    }

    @GetMapping
    public ResponseEntity getCategories() {
        try {
            return ResponseEntity.ok().body(cache.getCategories());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/name/{categoryName}")
    public ResponseEntity getSubcategoriesByCategoryName(@PathVariable CategoryEnum categoryName) {
        try {
            return ResponseEntity.ok().body(cache.getSubcategoriesByCategoryName(categoryName));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/id/{categoryId}")
    public ResponseEntity getSubcategoriesByCategoryId(@PathVariable UUID categoryId) {
        try {
            return ResponseEntity.ok().body(cache.getSubcategoriesByCategoryId(categoryId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
