package com.stroganova.onlineshop.service.impl;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
//@SessionScope
public class CartServiceDefault implements CartService {

    private List<Product> cart = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void addToCart(Product product) {
        cart.add(product);
    }

    @Override
    public List<Product> viewCart() {
        return cart;
    }
}
