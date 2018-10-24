package com.stroganova.onlineshop.entity;

public class User {

    private String login;
    private String password;
    private String salt;
    private UserRole role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setRole(String role) {

        this.role = UserRole.getByName(role);
    }

    public String getRole() {
        return role.getName();
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", role='" + role.getName() + '\'' +
                '}';
    }
}
