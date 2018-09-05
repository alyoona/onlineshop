package com.stroganova.onlineshop.dao.jdbc;

import com.stroganova.onlineshop.dao.ProductDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.stroganova.onlineshop.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private final static String GET_ALL_PRODUCTS_SQL =
            "SELECT id, name, description, price, \"picturePath\" FROM \"OnlineShopSchema\".\"Products\";";
    private final static String ADD_PRODUCT_SQL =
            "INSERT INTO \"OnlineShopSchema\".\"Products\"(name, description, price, \"picturePath\") VALUES (?, ?, ?, ?);";


    private ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private DataSource dataSource;

    @Override
    public List<Product> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PRODUCTS_SQL)) {
            List<Product> userList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                userList.add(product);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException("error while getting all products", e);
        }
    }

    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_SQL)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getPicturePath());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error while adding product", e);
        }
    }

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
