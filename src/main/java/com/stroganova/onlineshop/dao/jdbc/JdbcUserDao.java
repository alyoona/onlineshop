package com.stroganova.onlineshop.dao.jdbc;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.stroganova.onlineshop.entity.User;
import com.stroganova.onlineshop.exception.NoUniqueUserNameException;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JdbcUserDao implements UserDao {

    private final static String GET_USER_BY_LOGIN =
            "SELECT u.login, u.password, u.salt, r.role " +
                    "FROM onlineshopschema.users u " +
                    "JOIN onlineshopschema.user_roles ur ON u.id = ur.user_id " +
                    "JOIN onlineshopschema.roles r ON ur.role_id = r.id " +
                    "WHERE login = ?;";

    private final static String INSERT_USER_SQL =
            "INSERT INTO onlineshopschema.users (login, password, salt) VALUES (?, ?, ?);";

    private static final String INSERT_USER_ROLE_SQL =
            "INSERT INTO onlineshopschema.user_roles(user_id, role_id) " +
                    "SELECT u.id, r.id  FROM onlineshopschema.users u, onlineshopschema.roles r " +
                    "WHERE u.login = ? AND r.role = ?;";

    private DataSource dataSource;

    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public User getUser(String username) {
        LOGGER.info("Start of getting a user by username.");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN)
        ) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOGGER.info("The user by username have been got.");
                return USER_ROW_MAPPER.mapRow(resultSet);
            } else {
                LOGGER.warn("The user hasn't been found by username.");
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Error while finding user");
            throw new RuntimeException("Error while finding user", e);
        }
    }

    @Override
    public void add(User user) {
        LOGGER.info("Start of adding a user to DB.");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
             PreparedStatement preparedStatementSetUserRole = connection.prepareStatement(INSERT_USER_ROLE_SQL)
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.executeUpdate();
            LOGGER.info("The user \"{}\" has been added.", user.getLogin());
            preparedStatementSetUserRole.setString(1, user.getLogin());
            preparedStatementSetUserRole.setString(2, user.getRole());
            preparedStatementSetUserRole.executeUpdate();
            LOGGER.info("The user's role has been set to \"{}\".", user.getRole());
        } catch (SQLException sqlException) {
            PSQLException psqlException = (PSQLException) sqlException;
            ServerErrorMessage serverErrorMessage = psqlException.getServerErrorMessage();
            if (serverErrorMessage.getConstraint().contains("login")) {
                LOGGER.error("User's login should be unique.", sqlException);
                throw new NoUniqueUserNameException(serverErrorMessage.getMessage() + " | " + serverErrorMessage.getDetail(), sqlException);
            } else {
                LOGGER.error("Error while user registration.", sqlException);
                throw new RuntimeException("error while user registration: " + serverErrorMessage.getMessage() + " | " + serverErrorMessage.getDetail(), sqlException);
            }
        }
    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
