package com.ust.sdet.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {

    private DatabaseConfig() {}

    public static Connection getConnection() throws SQLException {

        String url = System.getProperty(
                "db.url",
                "jdbc:mysql://localhost:3306/testorderdb"
        );

        String user = System.getProperty(
                "db.user",
                "root"
        );

        String password = System.getProperty(
                "db.password",
                "admin"
        );

        return DriverManager.getConnection(url, user, password);
    }
}