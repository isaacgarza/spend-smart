package com.spendsmart.service;

import com.spendsmart.dto.Expense;

import java.util.Set;
import java.util.UUID;

public interface ExpenseService {

    Expense addExpense(Expense expense);

    void updateExpense(Expense expense);

    void deleteExpense(UUID expenseId);

    Set<Expense> getExpenses(UUID personId);
}
