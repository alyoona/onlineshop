package com.stroganova.onlineshop.dao.jdbc;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.stroganova.onlineshop.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao implements UserDao {

    private final static String FIND_USER_SQL = "SELECT login, password FROM \"OnlineShopSchema\".\"Users\" WHERE login = ? AND password = ?;";
    private final static String INSERT_USER_SQL = "INSERT INTO \"OnlineShopSchema\".\"Users\" (login, password) VALUES (?, ?);";
    private DataSource dataSource;
    private UserRowMapper userRowMapper = new UserRowMapper();

    @Override
    public User getUser(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_SQL)
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = userRowMapper.mapRow(resultSet);
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("error while finding user", e);
        }
    }

    @Override
    public void add(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error while user registration", e);
        }
    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
