package com.stroganova.onlineshop.entity;

import com.stroganova.onlineshop.exception.IncorrectUserRoleException;

public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public static UserRole getByName(String name) {
        UserRole[] values = values();
        for (UserRole role : values) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IncorrectUserRoleException("No user role for name: " + name);
    }

    public String getName() {
        return name;
    }

}
