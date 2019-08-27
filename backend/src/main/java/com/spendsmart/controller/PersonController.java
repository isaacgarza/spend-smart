package com.spendsmart.controller;

import com.spendsmart.dto.Person;
import com.spendsmart.service.ExpenseService;
import com.spendsmart.service.GoalService;
import com.spendsmart.service.PersonService;
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
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;
    private final ExpenseService expenseService;
    private final GoalService goalService;

    @Autowired
    public PersonController(PersonService personService, ExpenseService expenseService, GoalService goalService) {
        this.personService = personService;
        this.expenseService = expenseService;
        this.goalService = goalService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getPerson(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(personService.getPersonById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity getPeople() {
        try {
            return ResponseEntity.ok().body(personService.getPeople());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/expenses/{personId}")
    public ResponseEntity getExpenses(@PathVariable UUID personId) {
        try {
            return ResponseEntity.ok().body(expenseService.getExpenses(personId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/goals/{personId}")
    public ResponseEntity getGoals(@PathVariable UUID personId) {
        try {
            return ResponseEntity.ok().body(goalService.getGoals(personId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity addPerson(@Valid @RequestBody Person person) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(personService.addPerson(person));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity updatePerson(@Valid @RequestBody Person person) {
        try {
            personService.updatePerson(person);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return updateFailedResponse(e);
        }
    }

    @DeleteMapping(value = "/{personId}")
    public ResponseEntity deletePerson(@PathVariable UUID personId) {
        try {
            personService.deletePerson(personId);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity updateFailedResponse(ServiceException e) {
        if (e.getMessage().equals(ExceptionConstants.PERSON_NOT_FOUND)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
