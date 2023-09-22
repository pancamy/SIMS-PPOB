package com.sims.ppob.application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/db_sims_ppob?useSSL=false";
        String username = "postgres";
        String password = "postgres12345";

        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
