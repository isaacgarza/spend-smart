package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.cache.Cache;
import com.spendsmart.dto.Expense;
import com.spendsmart.entity.ExpenseTable;
import com.spendsmart.repository.ExpenseRepository;
import com.spendsmart.service.ExpenseService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.ExceptionConstants;
import com.spendsmart.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final Cache cache;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              Cache cache,
                              @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.expenseRepository = expenseRepository;
        this.cache = cache;
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional
    public Expense addExpense(Expense expense) {
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ExpenseTable expenseTable = mapExpenseToTable(expense);
        save(expense, expenseTable);
        expense.setId(expenseTable.getId());
        return expense;
    }

    @Transactional
    public void updateExpense(Expense expense) {
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ExpenseTable newExpenseTable = mapExpenseToTable(expense);
        Optional<ExpenseTable> expenseTable = expenseRepository.findById(expense.getId());
        if (expenseTable.isPresent()) {
            ServiceUtil.copyNonNullProperties(newExpenseTable, expenseTable.get());
            save(expense, expenseTable.get());
        } else {
            throw new ServiceException(ExceptionConstants.EXPENSE_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteExpense(UUID expenseId) {
        try {
            expenseRepository.deleteById(expenseId);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred deleting expense", e);
        }
    }

    @Transactional(readOnly = true)
    public Set<Expense> getExpenses(UUID personId) {
        try {
            return mapExpenseTableListToExpenses(expenseRepository.findAllByPersonId(personId));
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving expenses for " + personId, e);
        }
    }

    private ExpenseTable mapExpenseToTable(Expense expense) {
        return jacksonObjectMapper.convertValue(expense, ExpenseTable.class);
    }

    private Set<Expense> mapExpenseTableListToExpenses(Set<ExpenseTable> expenseTableList) {
        Set<Expense> expenses = new HashSet<>();
        expenseTableList.forEach(expenseTable -> {
            Expense expense = jacksonObjectMapper.convertValue(expenseTable, Expense.class);
            expense.setExpenseRepeatScheduleType(cache.getExpenseRepeatScheduleTypeEnum(expenseTable.getExpenseRepeatScheduleTypeId()));
            expense.setFundingScheduleType(cache.getFundingScheduleTypeEnum(expenseTable.getFundingScheduleTypeId()));
            expenses.add(expense);
        });
        return expenses;
    }

    private void save(Expense expense, ExpenseTable expenseTable) {
        try {
            UUID expenseRepeatScheduleTypeId = cache.getExpenseRepeatScheduleId(expense.getExpenseRepeatScheduleType());
            UUID fundingScheduleTypeId = cache.getFundingScheduleId(expense.getFundingScheduleType());
            expenseTable.setExpenseRepeatScheduleTypeId(expenseRepeatScheduleTypeId);
            expenseTable.setFundingScheduleTypeId(fundingScheduleTypeId);
            expenseRepository.save(expenseTable);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred adding/updating expense", e);
        }
    }
}
