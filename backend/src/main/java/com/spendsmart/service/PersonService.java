package com.spendsmart.service;

import com.spendsmart.dto.Person;

import java.util.Set;
import java.util.UUID;

public interface PersonService {

    Person addPerson(Person person);

    void updatePerson(Person person);

    void deletePerson(UUID personId);

    Set<Person> getPeople();

    Person getPersonById(UUID id);
}
