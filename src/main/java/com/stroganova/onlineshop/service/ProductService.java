package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    void add(Product product);

    Product getProduct(long id);
}
