package com.spendsmart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spendsmart.dto.User;
import com.spendsmart.entity.UserTable;
import com.spendsmart.repository.UserRepository;
import com.spendsmart.service.ServiceException;
import com.spendsmart.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.jacksonObjectMapper = objectMapper;
    }

    @Transactional
    public User addPerson(User user) {
        UserTable userTable = mapPersonToTable(user);
        save(userTable);
        user.setId(userTable.getId());
        return user;
    }

    @Transactional
    public void updatePerson(User user) {
        UserTable newUserTable = mapPersonToTable(user);
        Optional<UserTable> personTable = userRepository.findById(user.getId());
        if (personTable.isPresent()) {
            ServiceUtil.copyNonNullProperties(newUserTable, personTable.get());
            save(personTable.get());
        } else {
            throw new ServiceException(ExceptionConstants.PERSON_NOT_FOUND);
        }
    }

    @Transactional
    public void deletePerson(UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred deleting person", e);
        }
    }

    @Transactional(readOnly = true)
    public Set<User> getPeople() {
        try {
             return mapPersonTableListToPeople(userRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving set of people", e);
        }
    }

    @Transactional(readOnly = true)
    public User getPersonById(UUID id) {
        try {
            return mapTableToPerson(userRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving person by id", e);
        }
    }

    private User mapTableToPerson(Optional<UserTable> personTable) {
        if (personTable.isPresent()) {
            return jacksonObjectMapper.convertValue(personTable, User.class);
        } else {
            throw new ServiceException("Person not found");
        }
    }

    private UserTable mapPersonToTable(User user) {
        return jacksonObjectMapper.convertValue(user, UserTable.class);
    }

    private Set<User> mapPersonTableListToPeople(List<UserTable> userTableList) {
        Set<User> people = new HashSet<>();
        userTableList.forEach(userTable -> people.add(jacksonObjectMapper.convertValue(userTable, User.class)));
        return people;
    }

    private void save(UserTable userTable) {
        try {
            userRepository.save(userTable);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred adding/updating person", e);
        }
    }
}
