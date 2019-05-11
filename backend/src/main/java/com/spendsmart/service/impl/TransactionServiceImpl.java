package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.dto.Category;
import com.spendsmart.dto.Subcategory;
import com.spendsmart.service.ServiceException;
import com.spendsmart.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public TransactionServiceImpl(@Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional
    public void addTransactionCategory(String id, Category category) {
        try {

        } catch (Exception e) {
            throw new ServiceException("Exception occurred ", e);
        }
    }

    @Transactional
    public void addTransactionSubcategory(String id, Subcategory subcategory) {
        try {

        } catch (Exception e) {
            throw new ServiceException("Exception occurred ", e);
        }
    }
}
