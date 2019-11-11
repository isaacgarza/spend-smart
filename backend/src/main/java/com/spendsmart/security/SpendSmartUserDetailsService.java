package com.spendsmart.security;

import com.spendsmart.dto.User;
import com.spendsmart.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SpendSmartUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public SpendSmartUserDetailsService(UserService userService) {this.userService = userService;}

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.getUserByEmail(email);
        return UserPrincipal.create(user);
    }
}
