package com.utils;

import java.sql.*;

public class JDBCUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/food";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}