package com.stroganova.onlineshop.dao.jdbc;


import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.stroganova.onlineshop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private final static String GET_ALL_PRODUCTS_SQL =
            "SELECT id, name, description, price, picturePath FROM onlineshopschema.products;";
    private final static String ADD_PRODUCT_SQL =
            "INSERT INTO onlineshopschema.products(name, description, price, picturePath) VALUES (?, ?, ?, ?);";
    private final static String GET_PRODUCT_BY_ID_SQL =
            "SELECT id, name, description, price, picturePath FROM onlineshopschema.products WHERE id = ?;";

    private DataSource dataSource;

    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Product> getAll() {
        logger.info("Start of getting list of all products");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PRODUCTS_SQL)) {
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                productList.add(product);
            }
            logger.info("End of getting list of all products");
            logger.trace("The resulting products {}", productList);
            return productList;
        } catch (SQLException e) {
            logger.error("Something wrong while getting all products", e);
            throw new RuntimeException("Error while getting all products", e);
        }
    }

    @Override
    public void add(Product product) {
        logger.info("Start of adding a product to DB.");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_SQL)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getPicturePath());

            statement.executeUpdate();
            logger.info("The product {} has been added.", product);
        } catch (SQLException e) {
            logger.error("Error while adding product.", e);
            throw new RuntimeException("Error while adding product.", e);
        }
    }

    @Override
    public Product getProduct(long id) {
        logger.info("Start of getting a product by id.");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                    logger.info("The {} has been got.", product);
                    return product;
                }
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error while getting product by id", e);
            throw new RuntimeException("Error while getting product by id", e);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
