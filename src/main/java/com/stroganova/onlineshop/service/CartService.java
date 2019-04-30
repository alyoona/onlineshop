package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Product;

import java.util.List;

public interface CartService {

    void addToCart(Product product);

    List<Product> viewCart();
}
