package com.stroganova.onlineshop.dao.jdbc.mapper;

import com.stroganova.onlineshop.entity.User;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {
    @Test
    public void mapRow() throws Exception {
        //prepare

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("login")).thenReturn("testUserName");
        when(mockResultSet.getString("password")).thenReturn("testUserPassword");

        //when
        UserRowMapper userRowMapper = new UserRowMapper();
        User actualUser = userRowMapper.mapRow(mockResultSet);

        //then

        assertNotNull(actualUser);
        assertEquals("testUserName", actualUser.getLogin());
        assertEquals("testUserPassword", actualUser.getPassword());
        
    }

}