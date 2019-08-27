package com.spendsmart.service;

import com.spendsmart.dto.User;

import java.util.Set;
import java.util.UUID;

public interface UserService {

    User addPerson(User user);

    void updatePerson(User user);

    void deletePerson(UUID userId);

    Set<User> getPeople();

    User getPersonById(UUID id);
}
