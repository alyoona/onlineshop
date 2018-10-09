package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.entity.Product;

import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public void add(Product product) {
        productDao.add(product);
    }

    public Product getProduct(long id) {
        return productDao.getProduct(id);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
