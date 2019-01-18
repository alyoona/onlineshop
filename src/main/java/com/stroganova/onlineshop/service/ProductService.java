package com.stroganova.onlineshop.service;

import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public void add(Product product) {
        productDao.add(product);
    }

    public Product getProduct(long id) {
        return productDao.getProduct(id);
    }

}
