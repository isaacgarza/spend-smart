package com.spendsmart.controller;

import com.spendsmart.dto.Expense;
import com.spendsmart.service.ExpenseService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(value = "/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity addExpense(@Valid @RequestBody Expense expense) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(expense));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity updateExpense(@Valid @RequestBody Expense expense) {
        try {
            expenseService.updateExpense(expense);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return updateFailedResponse(e);
        }
    }

    @DeleteMapping(value = "/{expenseId}")
    public ResponseEntity deleteExpense(@PathVariable UUID expenseId) {
        try {
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity updateFailedResponse(ServiceException e) {
        if (e.getMessage().equals(ExceptionConstants.EXPENSE_NOT_FOUND)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
