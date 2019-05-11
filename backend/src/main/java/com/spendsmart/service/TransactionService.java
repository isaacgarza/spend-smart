package com.spendsmart.service;

import com.spendsmart.dto.Category;
import com.spendsmart.dto.Subcategory;

public interface TransactionService {

    void addTransactionCategory(String id, Category category);

    void addTransactionSubcategory(String id, Subcategory subcategory);
}
