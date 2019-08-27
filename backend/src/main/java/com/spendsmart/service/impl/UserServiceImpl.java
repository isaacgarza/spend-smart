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
    public User addUser(User user) {
        UserTable userTable = mapUserToTable(user);
        save(userTable);
        user.setId(userTable.getId());
        return user;
    }

    @Transactional
    public void updateUser(User user) {
        UserTable newUserTable = mapUserToTable(user);
        Optional<UserTable> userTable = userRepository.findById(user.getId());
        if (userTable.isPresent()) {
            ServiceUtil.copyNonNullProperties(newUserTable, userTable.get());
            save(userTable.get());
        } else {
            throw new ServiceException(ExceptionConstants.USER_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteUser(UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred deleting user", e);
        }
    }

    @Transactional(readOnly = true)
    public Set<User> getUsers() {
        try {
             return mapUserTableListToUsers(userRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving set of users", e);
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        try {
            return mapTableToUser(id);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving user by id", e);
        }
    }

    private User mapTableToUser(UUID id) {
        Optional<UserTable> userTable = userRepository.findById(id);
        if (userTable.isPresent()) {
            return jacksonObjectMapper.convertValue(userTable, User.class);
        } else {
            throw new ServiceException("User not found");
        }
    }

    private UserTable mapUserToTable(User user) {
        return jacksonObjectMapper.convertValue(user, UserTable.class);
    }

    private Set<User> mapUserTableListToUsers(List<UserTable> userTableList) {
        Set<User> users = new HashSet<>();
        userTableList.forEach(userTable -> users.add(jacksonObjectMapper.convertValue(userTable, User.class)));
        return users;
    }

    private void save(UserTable userTable) {
        try {
            userRepository.save(userTable);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred adding/updating user", e);
        }
    }
}
