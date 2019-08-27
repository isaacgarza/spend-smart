package com.spendsmart.service;

import com.spendsmart.dto.PersonSubcategory;

import java.util.Set;
import java.util.UUID;

public interface PersonSubcategoryService {

    PersonSubcategory addCustomSubcategory(PersonSubcategory personSubcategory);

    Set<PersonSubcategory> getCustomSubcategories(UUID userId);
}
