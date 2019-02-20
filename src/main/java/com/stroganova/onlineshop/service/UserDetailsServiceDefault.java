package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.entity.UserDetailsDefault;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceDefault implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceDefault(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        return new UserDetailsDefault(user);
    }
}



