package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.User;

public interface SecurityService {

    User register(String username, String password);
}
