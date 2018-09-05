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

    private final static String FIND_USER_SQL = "SELECT * FROM \"OnlineShopSchema\".\"Users\" WHERE login = ? AND password = ?;";
    private final static String FIND_USER_BY_TOKEN_SQL = "SELECT * FROM \"OnlineShopSchema\".\"Users\" WHERE user_token = ?;";
    private final static String FIND_USER_BY_LOGIN_SQL = "SELECT * FROM \"OnlineShopSchema\".\"Users\" WHERE login = ?;";
    private final static String UPDATE_USER_TOKEN_SQL = "UPDATE \"OnlineShopSchema\".\"Users\" SET user_token = ? WHERE login = ? AND password = ?;";
    private final static String INSERT_USER_SQL = "INSERT INTO \"OnlineShopSchema\".\"Users\" (login, password, user_token) VALUES (?, ?, ?);";
    private DataSource dataSource;
    private UserRowMapper userRowMapper = new UserRowMapper();

    @Override
    public boolean isIdentified(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_SQL)
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("error while finding user", e);
        }
    }

    @Override
    public boolean isIdentified(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_SQL)
        ) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("error while finding user", e);
        }
    }

    @Override
    public User getUser(String userToken) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_TOKEN_SQL)
        ) {
            preparedStatement.setString(1, userToken);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = userRowMapper.mapRow(resultSet);
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("error while finding user login value", e);
        }
    }

    @Override
    public void setUserToken(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_TOKEN_SQL)
        ) {
            preparedStatement.setString(1, user.getUserToken());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error while updating user_token value", e);
        }
    }

    @Override
    public void add(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getUserToken());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error while user registration", e);
        }
    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
