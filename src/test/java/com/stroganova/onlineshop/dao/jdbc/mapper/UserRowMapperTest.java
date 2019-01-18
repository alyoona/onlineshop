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
        when(mockResultSet.getString("salt")).thenReturn("testSalt");
        when(mockResultSet.getString("role")).thenReturn("adMin");
        //when
        UserRowMapper userRowMapper = new UserRowMapper();
        User actualUser = userRowMapper.mapRow(mockResultSet, 0);

        //then

        assertNotNull(actualUser);
        assertEquals("testUserName", actualUser.getLogin());
        assertEquals("testUserPassword", actualUser.getPassword());
        assertEquals("testSalt", actualUser.getSalt());
        assertEquals("admin", actualUser.getRole());
        
    }

}