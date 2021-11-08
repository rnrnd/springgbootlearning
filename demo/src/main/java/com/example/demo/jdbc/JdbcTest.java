package com.example.demo.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.UUID;

public class JdbcTest {

    public static final String URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    private Connection connection;

    @Before
    public void init() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    @After
    public void over() throws SQLException {
        connection.close();
    }
    @Test
    public void jdbcTest() throws SQLException {
        String sql = "select * from users where `name`=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();
        preparedStatement.close();
    }
    @Test
    public void preparedBatchTest() throws SQLException {
        String sql = "INSERT INTO users (`id`,`age`) VALUES (?,29)";
        PreparedStatement statement = connection.prepareStatement(sql);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.execute();
        }
        System.out.println(System.currentTimeMillis() - l);
        l = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.addBatch();
        }
        statement.executeBatch();
        System.out.println(System.currentTimeMillis() - l);
        statement.close();
    }
    //PreparedStatement防止 sql注入测试
    @Test
    public void prepareTest() throws SQLException {
        String id = UUID.randomUUID().toString();
        String sql = "INSERT INTO users (`id`,`name`,`age`) VALUES ('"+id+"', 'Kawhi Leonard',29)";
        PreparedStatement statement = connection.prepareStatement(sql);
        // 第一次
        statement.execute();
        statement.getResultSet();


    }
}
