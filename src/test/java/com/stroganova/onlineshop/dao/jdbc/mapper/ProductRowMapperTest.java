package com.stroganova.onlineshop.dao.jdbc.mapper;

import com.stroganova.onlineshop.entity.Product;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ProductRowMapperTest {

    @Test
    public void mapRowTest() throws SQLException {
        //prepare
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("duck");
        when(mockResultSet.getString("description")).thenReturn("little toy");
        when(mockResultSet.getDouble("price")).thenReturn(125.05);
        when(mockResultSet.getString("picturePath")).thenReturn("http://online-shop.com");

        //when
        ProductRowMapper productRowMapper = new ProductRowMapper();
        Product actualProduct = productRowMapper.mapRow(mockResultSet,0);

        //then
        assertNotNull(actualProduct);
        assertEquals(1L, actualProduct.getId());
        assertEquals("duck", actualProduct.getName());
        assertEquals("little toy", actualProduct.getDescription());
        assertEquals(125.05, actualProduct.getPrice(), 0.01);
        assertEquals("http://online-shop.com", actualProduct.getPicturePath());
    }
}