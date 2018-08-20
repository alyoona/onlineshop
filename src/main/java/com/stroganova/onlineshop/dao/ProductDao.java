package com.stroganova.onlineshop.dao;

import com.stroganova.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    void add(Product product);
}
