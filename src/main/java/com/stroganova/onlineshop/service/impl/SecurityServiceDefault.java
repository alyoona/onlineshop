package com.stroganova.onlineshop.service.impl;

import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.entity.UserDetailsDefault;
import com.stroganova.onlineshop.entity.UserRole;
import com.stroganova.onlineshop.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceDefault implements SecurityService {

    private UserServiceDefault userService;
    private PasswordEncoder encoder;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SecurityServiceDefault(UserServiceDefault userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public void register(String username, String password) {
        logger.info("Starting of user registration.");
        User user = new User();
        user.setLogin(username);
        user.setPassword(encoder.encode(password));
        user.setRole(UserRole.USER.getName());
        userService.add(user);
        autoLogin(user);
    }

    private void autoLogin(User user) {
        UserDetails userDetails = new UserDetailsDefault(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getLogin()
                , user.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext()
                .setAuthentication(token);
        logger.debug("Auto login successfully: {}", user.getLogin());
    }


}
