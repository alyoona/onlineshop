package com.stroganova.onlineshop.entity;

public class Session {

    String token;


    User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Session{");
        sb.append("token='").append(token).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }


}
