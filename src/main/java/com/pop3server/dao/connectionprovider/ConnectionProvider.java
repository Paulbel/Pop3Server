package com.pop3server.dao.connectionprovider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionProvider {
    public static ConnectionProvider instance = new ConnectionProvider();

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mail?autoReconnect=true&useSSL=false", "root", "root");
    }

    public ConnectionProvider getInstance() {
        return instance;
    }

    private ConnectionProvider() {
    }
}
