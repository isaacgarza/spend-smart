package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.dto.Person;
import com.spendsmart.entity.PersonTable;
import com.spendsmart.repository.PersonRepository;
import com.spendsmart.service.PersonService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.ExceptionConstants;
import com.spendsmart.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository,
                             @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.personRepository = personRepository;
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional
    public Person addPerson(Person person) {
        PersonTable personTable = mapPersonToTable(person);
        save(personTable);
        person.setId(personTable.getId());
        return person;
    }

    @Transactional
    public void updatePerson(Person person) {
        PersonTable newPersonTable = mapPersonToTable(person);
        Optional<PersonTable> personTable = personRepository.findById(person.getId());
        if (personTable.isPresent()) {
            ServiceUtil.copyNonNullProperties(newPersonTable, personTable.get());
            save(personTable.get());
        } else {
            throw new ServiceException(ExceptionConstants.PERSON_NOT_FOUND);
        }
    }

    @Transactional
    public void deletePerson(UUID personId) {
        try {
            personRepository.deleteById(personId);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred deleting person", e);
        }
    }

    @Transactional(readOnly = true)
    public Set<Person> getPeople() {
        try {
             return mapPersonTableListToPeople(personRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving set of people", e);
        }
    }

    private PersonTable mapPersonToTable(Person person) {
        return jacksonObjectMapper.convertValue(person, PersonTable.class);
    }

    private Set<Person> mapPersonTableListToPeople(List<PersonTable> personTableList) {
        Set<Person> people = new HashSet<>();
        personTableList.forEach(personTable -> people.add(jacksonObjectMapper.convertValue(personTable, Person.class)));
        return people;
    }

    private void save(PersonTable personTable) {
        try {
            personRepository.save(personTable);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred adding/updating person", e);
        }
    }
}
