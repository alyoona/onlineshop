package com.stroganova.onlineshop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Session {

    private String token;
    private User user;
    private LocalDateTime expireDate;
    private List<Product> cart = Collections.synchronizedList(new ArrayList<>());

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

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "\nSession{" +
                "token='" + token + '\'' +
                ", user=" + user +
                ", expireDate=" + expireDate +
                ", cart=" + cart +
                "}";
    }
}
