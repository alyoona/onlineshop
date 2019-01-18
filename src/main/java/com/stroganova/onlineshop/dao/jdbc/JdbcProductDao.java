package com.stroganova.onlineshop.dao.jdbc;


import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.stroganova.onlineshop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class JdbcProductDao implements ProductDao {

    private final static String GET_ALL_PRODUCTS_SQL =
            "SELECT id, name, description, price, picturePath FROM onlineshopschema.products;";

    private final static String ADD_PRODUCT_SQL =
            "INSERT INTO onlineshopschema.products(name, description, price, picturePath) VALUES (:name, :description, :price, :picturePath);";

    private final static String GET_PRODUCT_BY_ID_SQL =
            "SELECT id, name, description, price, picturePath FROM onlineshopschema.products WHERE id = :id;";

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcProductDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Product> getAll() {
        LOGGER.info("Start of getting list of all products");
        return namedParameterJdbcTemplate.query(GET_ALL_PRODUCTS_SQL, PRODUCT_ROW_MAPPER);
    }

    @Override
    public void add(Product product) {
        LOGGER.info("Start of adding a product to DB.");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("description", product.getDescription());
        parameterSource.addValue("price", product.getPrice());
        parameterSource.addValue("picturePath", product.getPicturePath());
        namedParameterJdbcTemplate.update(ADD_PRODUCT_SQL, parameterSource);
    }

    @Override
    public Product getProduct(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(GET_PRODUCT_BY_ID_SQL, parameterSource, PRODUCT_ROW_MAPPER);
    }


}
