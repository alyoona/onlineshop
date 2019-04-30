package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.entity.Product;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {

    List<Product> getAll();

    void add(Product product);

    Product getProduct(long id);

    void delete(long id);

    void update(Product product);
}
