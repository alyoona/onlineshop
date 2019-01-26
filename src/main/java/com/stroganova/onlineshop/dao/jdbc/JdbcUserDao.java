package com.stroganova.onlineshop.dao.jdbc;

import com.stroganova.onlineshop.dao.UserDao;
import com.stroganova.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.stroganova.onlineshop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class JdbcUserDao implements UserDao {

    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String getUserByLoginSQL;
    private String insertUserSQL;
    private String insertUserRoleSQL;

    public JdbcUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public User getUser(String login) {
        LOGGER.info("Start of getting a user by username.");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("login", login);
        return namedParameterJdbcTemplate.queryForObject(getUserByLoginSQL, parameterSource, USER_ROW_MAPPER);
    }

    @Override
    @Transactional
    public void add(User user) {
        LOGGER.info("Start of adding a user to DB.");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("login", user.getLogin());
        parameterSource.addValue("password", user.getPassword());
        parameterSource.addValue("salt", user.getSalt());
        parameterSource.addValue("role", user.getRole());
        namedParameterJdbcTemplate.update(insertUserSQL, parameterSource);
        LOGGER.info("First insert has been done");
        namedParameterJdbcTemplate.update(insertUserRoleSQL, parameterSource);
        LOGGER.info("Second insert has been done");
    }


    @Autowired
    public void setGetUserByLoginSQL(String getUserByLoginSQL) {
        this.getUserByLoginSQL = getUserByLoginSQL;
    }

    @Autowired
    public void setInsertUserSQL(String insertUserSQL) {
        this.insertUserSQL = insertUserSQL;
    }

    @Autowired
    public void setInsertUserRoleSQL(String insertUserRoleSQL) {
        this.insertUserRoleSQL = insertUserRoleSQL;
    }

}
