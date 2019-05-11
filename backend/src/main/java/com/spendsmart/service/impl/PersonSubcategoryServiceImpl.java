package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.dto.Category;
import com.spendsmart.dto.PersonSubcategory;
import com.spendsmart.entity.CategoryTable;
import com.spendsmart.entity.PersonSubcategoryTable;
import com.spendsmart.repository.CategoryRepository;
import com.spendsmart.repository.PersonSubcategoryRepository;
import com.spendsmart.service.PersonSubcategoryService;
import com.spendsmart.service.ServiceException;
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
public class PersonSubcategoryServiceImpl implements PersonSubcategoryService {

    private final PersonSubcategoryRepository personSubcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public PersonSubcategoryServiceImpl(PersonSubcategoryRepository personSubcategoryRepository,
                                        CategoryRepository categoryRepository,
                                        @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.personSubcategoryRepository = personSubcategoryRepository;
        this.categoryRepository = categoryRepository;
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional
    public PersonSubcategory addCustomSubcategory(PersonSubcategory personSubcategory) {
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PersonSubcategoryTable personSubcategoryTable = jacksonObjectMapper.convertValue(personSubcategory, PersonSubcategoryTable.class);
        save(personSubcategory, personSubcategoryTable);
        personSubcategory.setId(personSubcategoryTable.getId());
        return personSubcategory;
    }

    @Transactional(readOnly = true)
    public Set<PersonSubcategory> getCustomSubcategories(UUID personId) {
        try {
            return mapPersonSubcategoryTableListToPersonSubcategories(personSubcategoryRepository.findAllByPersonId(personId));
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving subcategories", e);
        }
    }

    private Set<PersonSubcategory> mapPersonSubcategoryTableListToPersonSubcategories(
            Set<PersonSubcategoryTable> personSubcategoryTableList) {
        Set<PersonSubcategory> personSubcategories = new HashSet<>();
        personSubcategoryTableList.forEach(personSubcategoryTable -> {
            PersonSubcategory personSubcategory =
                    jacksonObjectMapper.convertValue(personSubcategoryTable, PersonSubcategory.class);
            Category category = getCategoryById(personSubcategoryTable.getCategoryId());
            personSubcategory.setCategory(category);
            personSubcategories.add(personSubcategory);
        });
        return personSubcategories;
    }

    private void save(PersonSubcategory personSubcategory, PersonSubcategoryTable personSubcategoryTable) {
        try {
            UUID categoryId = getCategoryIdByName(personSubcategory.getCategory().getName());
            personSubcategoryTable.setCategoryId(categoryId);
            personSubcategoryRepository.save(personSubcategoryTable);
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
