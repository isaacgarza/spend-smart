package com.spendsmart.controller;

import com.spendsmart.dto.CustomSchedule;
import com.spendsmart.service.CustomScheduleService;
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
@RequestMapping(value = "/custom-schedule")
public class CustomFundingScheduleController {
    private final CustomScheduleService customScheduleService;

    @Autowired
    public CustomFundingScheduleController(CustomScheduleService customScheduleService) {
        this.customScheduleService = customScheduleService;
    }

    @GetMapping
    public ResponseEntity getCustomSchedules() {
        try {
            return ResponseEntity.ok().body(customScheduleService.getAllCustomSchedules());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity addCustomSchedule(@Valid @RequestBody CustomSchedule customSchedule) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customScheduleService.addCustomSchedule(customSchedule));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity updateCustomSchedule(@Valid @RequestBody CustomSchedule customSchedule) {
        try {
            customScheduleService.updateCustomSchedule(customSchedule);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return updateFailedResponse(e);
        }
    }

    @DeleteMapping(value = "/{customScheduleId}")
    public ResponseEntity deleteCustomSchedule(@PathVariable UUID customScheduleId) {
        try {
            customScheduleService.deleteCustomSchedule(customScheduleId);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity updateFailedResponse(ServiceException e) {
        if (e.getMessage().equals(ExceptionConstants.CUSTOM_SCHEDULE_NOT_FOUND)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
