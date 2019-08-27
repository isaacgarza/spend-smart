package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.dto.Category;
import com.spendsmart.dto.UserSubcategory;
import com.spendsmart.entity.CategoryTable;
import com.spendsmart.entity.UserSubcategoryTable;
import com.spendsmart.repository.CategoryRepository;
import com.spendsmart.repository.UserSubcategoryRepository;
import com.spendsmart.service.UserSubcategoryService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.service.UserSubcategoryService;
import com.spendsmart.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserSubcategoryServiceImpl implements UserSubcategoryService {

    private final UserSubcategoryRepository userSubcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public UserSubcategoryServiceImpl(UserSubcategoryRepository userSubcategoryRepository,
                                      CategoryRepository categoryRepository,
                                      @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.userSubcategoryRepository = userSubcategoryRepository;
        this.categoryRepository = categoryRepository;
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional
    public UserSubcategory addCustomSubcategory(UserSubcategory userSubcategory) {
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserSubcategoryTable userSubcategoryTable = jacksonObjectMapper.convertValue(userSubcategory, UserSubcategoryTable.class);
        save(userSubcategory, userSubcategoryTable);
        userSubcategory.setId(userSubcategoryTable.getId());
        return userSubcategory;
    }

    @Transactional(readOnly = true)
    public Set<UserSubcategory> getCustomSubcategories(UUID userId) {
        try {
            return mapPersonSubcategoryTableListToPersonSubcategories(userSubcategoryRepository.findAllByPersonId(userId));
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving subcategories", e);
        }
    }

    private Set<UserSubcategory> mapPersonSubcategoryTableListToPersonSubcategories(
            Set<UserSubcategoryTable> userSubcategoryTableList) {
        Set<UserSubcategory> personSubcategories = new HashSet<>();
        userSubcategoryTableList.forEach(userSubcategoryTable -> {
            UserSubcategory userSubcategory =
                    jacksonObjectMapper.convertValue(userSubcategoryTable, UserSubcategory.class);
            Category category = getCategoryById(userSubcategoryTable.getCategoryId());
            userSubcategory.setCategory(category);
            personSubcategories.add(userSubcategory);
        });
        return personSubcategories;
    }

    private void save(UserSubcategory userSubcategory, UserSubcategoryTable userSubcategoryTable) {
        try {
            UUID categoryId = getCategoryIdByName(userSubcategory.getCategory().getName());
            userSubcategoryTable.setCategoryId(categoryId);
            userSubcategoryRepository.save(userSubcategoryTable);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred adding/updating custom subcategory", e);
        }
    }

    public Category getCategoryById(UUID categoryId) {
        Optional<CategoryTable> categoryTable = categoryRepository.findById(categoryId);
        if (categoryTable.isPresent()) {
            return jacksonObjectMapper.convertValue(categoryTable, Category.class);
        } else {
            throw new ServiceException(ExceptionConstants.CATEGORY_NOT_FOUND);
        }
    }

    public UUID getCategoryIdByName(String categoryName) {
        Optional<CategoryTable> categoryTable = categoryRepository.findByName(categoryName);
        if (categoryTable.isPresent()) {
            return categoryTable.get().getId();
        } else {
            throw new ServiceException(ExceptionConstants.CATEGORY_NOT_FOUND);
        }
    }
}
