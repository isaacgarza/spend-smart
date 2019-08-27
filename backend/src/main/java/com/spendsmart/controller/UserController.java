package com.spendsmart.controller;

import com.spendsmart.dto.User;
import com.spendsmart.service.ExpenseService;
import com.spendsmart.service.GoalService;
import com.spendsmart.service.UserService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final ExpenseService expenseService;
    private final GoalService goalService;

    @Autowired
    public UserController(UserService userService, ExpenseService expenseService, GoalService goalService) {
        this.userService = userService;
        this.expenseService = expenseService;
        this.goalService = goalService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUser(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity getUsers() {
        try {
            return ResponseEntity.ok().body(userService.getUsers());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/expenses/{userId}")
    public ResponseEntity getExpenses(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok().body(expenseService.getExpenses(userId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/goals/{userId}")
    public ResponseEntity getGoals(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok().body(goalService.getGoals(userId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity updateUser(@Valid @RequestBody User user) {
        try {
            userService.updateUser(user);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return updateFailedResponse(e);
        }
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity deleteUser(@PathVariable UUID userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity updateFailedResponse(ServiceException e) {
        if (e.getMessage().equals(ExceptionConstants.USER_NOT_FOUND)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
