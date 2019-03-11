package com.stroganova.onlineshop.service.impl;

import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceDefault implements ProductService {

    private ProductDao productDao;

    public ProductServiceDefault(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public void add(Product product) {
        productDao.add(product);
    }

    @Override
    public Product getProduct(long id) {
        return productDao.getProduct(id);
    }
}
