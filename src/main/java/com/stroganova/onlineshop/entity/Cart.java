package com.stroganova.onlineshop.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> cart = new ArrayList<>();

    public List<Product> getProducts() {
        return cart;
    }

    public void addToCart(Product product) {cart.add(product);
    }
}
