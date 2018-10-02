package com.stroganova.onlineshop.dao.jdbc;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.exception.NoUniqueUserNameException;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JdbcUserDao implements UserDao {

    private final static String GET_USER_BY_LOGIN =
            "SELECT u.login, u.password, u.salt, r.role " +
            "FROM OnlineShopSchema.Users u " +
            "JOIN OnlineShopSchema.user_roles ur ON u.id = ur.user_id " +
            "JOIN OnlineShopSchema.roles r ON ur.role_id = r.id " +
            "WHERE login = ?;";

    private final static String INSERT_USER_SQL =
            "INSERT INTO OnlineShopSchema.Users (login, password, salt) VALUES (?, ?, ?);";

    private static final String INSERT_USER_ROLE_SQL =
            "INSERT INTO onlineshopschema.user_roles(user_id, role_id) " +
            "select u.id, r.id  from OnlineShopSchema.Users u, OnlineShopSchema.roles r " +
            "where u.login = ? and r.role = ?;";

    private DataSource dataSource;

    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    @Override
    public User getUser(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN)
        ) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return USER_ROW_MAPPER.mapRow(resultSet);
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
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
             PreparedStatement preparedStatementSetUserRole = connection.prepareStatement(INSERT_USER_ROLE_SQL)
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.executeUpdate();

            preparedStatementSetUserRole.setString(1, user.getLogin());
            preparedStatementSetUserRole.setString(2, "user");
            preparedStatementSetUserRole.executeUpdate();
        } catch (SQLException sqlException) {
            PSQLException psqlException = (PSQLException) sqlException;
            ServerErrorMessage serverErrorMessage = psqlException.getServerErrorMessage();
            if ("users_login_uindex".equals(serverErrorMessage.getConstraint())) {
                throw new NoUniqueUserNameException(serverErrorMessage.getMessage() + " | " + serverErrorMessage.getDetail(), sqlException);
            } else {
                throw new RuntimeException("error while user registration: " + serverErrorMessage.getMessage() + " | " + serverErrorMessage.getDetail(), sqlException);
            }
        }
    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
