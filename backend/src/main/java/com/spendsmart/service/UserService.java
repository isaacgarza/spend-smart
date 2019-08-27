package com.spendsmart.service;

import com.spendsmart.dto.User;

import java.util.Set;
import java.util.UUID;

public interface UserService {

    User addUser(User user);

    void updateUser(User user);

    void deleteUser(UUID userId);

    Set<User> getUsers();

    User getUserById(UUID id);
}
