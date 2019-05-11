package com.spendsmart.controller;

import com.spendsmart.dto.Goal;
import com.spendsmart.service.GoalService;
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
@RequestMapping(value = "/goal")
public class GoalController {
    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity addGoal(@Valid @RequestBody Goal goal) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(goalService.addGoal(goal));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity updateGoal(@Valid @RequestBody Goal goal) {
        try {
            goalService.updateGoal(goal);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return updateFailedResponse(e);
        }
    }

    @DeleteMapping(value = "/{goalId}")
    public ResponseEntity deleteGoal(@PathVariable UUID goalId) {
        try {
            goalService.deleteGoal(goalId);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity updateFailedResponse(ServiceException e) {
        if (e.getMessage().equals(ExceptionConstants.GOAL_NOT_FOUND)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
