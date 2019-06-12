package com.ecommerce.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql {
    public Connection getConecction() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
