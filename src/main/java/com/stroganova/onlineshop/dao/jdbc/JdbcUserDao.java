package com.stroganova.onlineshop.dao.jdbc;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.stroganova.onlineshop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcUserDao implements UserDao {

    private final static String GET_USER_BY_LOGIN =
            "SELECT u.login, u.password, u.salt, r.role " +
                    "FROM onlineshopschema.users u " +
                    "JOIN onlineshopschema.user_roles ur ON u.id = ur.user_id " +
                    "JOIN onlineshopschema.roles r ON ur.role_id = r.id " +
                    "WHERE login = :login;";

    private final static String INSERT_USER_SQL =
            "INSERT INTO onlineshopschema.users (login, password, salt) VALUES (:login, :password, :salt);";

    private static final String INSERT_USER_ROLE_SQL =
            "INSERT INTO onlineshopschema.user_roles(user_id, role_id) " +
                    "SELECT u.id, r.id  FROM onlineshopschema.users u, onlineshopschema.roles r " +
                    "WHERE u.login = :login AND r.role = :role;";

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public User getUser(String login) {
        LOGGER.info("Start of getting a user by username.");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("login", login);
        return namedParameterJdbcTemplate.queryForObject(GET_USER_BY_LOGIN, parameterSource, USER_ROW_MAPPER);
    }

    @Override
    public void add(User user) {
        LOGGER.info("Start of adding a user to DB.");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("login", user.getLogin());
        parameterSource.addValue("password", user.getPassword());
        parameterSource.addValue("salt", user.getSalt());
        parameterSource.addValue("role", user.getRole());
        namedParameterJdbcTemplate.update(INSERT_USER_SQL, parameterSource);
        namedParameterJdbcTemplate.update(INSERT_USER_ROLE_SQL, parameterSource);
    }

}
