package com.stroganova.onlineshop.dao.jdbc.mapper;

import com.stroganova.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {

    public Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getDouble("price"));
        product.setPicturePath(resultSet.getString("picturePath"));
        return product;
    }
}
