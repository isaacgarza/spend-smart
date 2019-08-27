package com.spendsmart.service;

import com.spendsmart.dto.UserSubcategory;

import java.util.Set;
import java.util.UUID;

public interface UserSubcategoryService {

    UserSubcategory addCustomSubcategory(UserSubcategory userSubcategory);

    Set<UserSubcategory> getCustomSubcategories(UUID userId);
}
