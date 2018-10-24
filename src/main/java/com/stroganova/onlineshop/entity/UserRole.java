package com.stroganova.onlineshop.entity;

import com.stroganova.onlineshop.exception.IncorrectUserRoleException;

public enum UserRole {
    USER("user"), ADMIN("admin");

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

    public static void main(String[] args) {
        System.out.println(UserRole.USER);
    }
}
