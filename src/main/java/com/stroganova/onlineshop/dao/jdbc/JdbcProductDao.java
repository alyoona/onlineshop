package com.stroganova.onlineshop.dao.jdbc;


import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.stroganova.onlineshop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String getAllProductsSQL;
    private String addProductSQL;
    private String getProductByIdSQL;
    private String deleteProductByIdSql;
    private String updateProductSQL;

    public JdbcProductDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Product> getAll() {
        LOGGER.info("Start of getting list of all products");
        return namedParameterJdbcTemplate.query(getAllProductsSQL, PRODUCT_ROW_MAPPER);
    }

    @Override
    public void add(Product product) {
        LOGGER.info("Start of adding a product to DB.");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("description", product.getDescription());
        parameterSource.addValue("price", product.getPrice());
        parameterSource.addValue("picturePath", product.getPicturePath());
        namedParameterJdbcTemplate.update(addProductSQL, parameterSource);
    }

    @Override
    public Product getProduct(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(getProductByIdSQL, parameterSource, PRODUCT_ROW_MAPPER);
    }

    @Override
    public void delete(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        namedParameterJdbcTemplate.update(deleteProductByIdSql, parameterSource);
    }

    @Override
    public void update(Product product) {
        LOGGER.info("Start of updating a product inside DB");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", product.getId());
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("description", product.getDescription());
        parameterSource.addValue("price", product.getPrice());
        parameterSource.addValue("picturePath", product.getPicturePath());
        namedParameterJdbcTemplate.update(updateProductSQL, parameterSource);
    }


    @Autowired
    public void setGetAllProductsSQL(String getAllProductsSQL) {
        this.getAllProductsSQL = getAllProductsSQL;
    }
    @Autowired
    public void setAddProductSQL(String addProductSQL) {
        this.addProductSQL = addProductSQL;
    }
    @Autowired
    public void setGetProductByIdSQL(String getProductByIdSQL) {
        this.getProductByIdSQL = getProductByIdSQL;
    }
    @Autowired
    public void setDeleteProductByIdSql(String deleteProductByIdSql) {
        this.deleteProductByIdSql = deleteProductByIdSql;
    }
    @Autowired
    public void setUpdateProductSQL(String updateProductSQL) {
        this.updateProductSQL = updateProductSQL;
    }

}
