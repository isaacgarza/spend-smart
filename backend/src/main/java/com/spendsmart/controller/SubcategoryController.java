package com.spendsmart.controller;

import com.spendsmart.cache.Cache;
import com.spendsmart.dto.UserSubcategory;
import com.spendsmart.service.UserSubcategoryService;
import com.spendsmart.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(value = "/subcategory")
public class SubcategoryController {

    private final Cache cache;
    private final UserSubcategoryService userSubcategoryService;

    @Autowired
    public SubcategoryController(Cache cache,
                                 UserSubcategoryService userSubcategoryService) {
        this.cache = cache;
        this.userSubcategoryService = userSubcategoryService;
    }

    @PostMapping
    public ResponseEntity addCustomSubcategory(@Valid @RequestBody UserSubcategory userSubcategory) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userSubcategoryService.addCustomSubcategory(userSubcategory));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity getSubcategories() {
        try {
            return ResponseEntity.ok().body(cache.getSubcategories());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity getSubcategoriesByPerson(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok().body(cache.getSubcategoriesByPerson(userId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
