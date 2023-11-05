package com.example.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class Config {
    Connection conn = null;
    public static Connection connectdb()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanager","admin","0000");
            return conn;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
//public class Config {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/taskmanager";
//    private static final String DB_USER = "admin";
//    private static final String DB_PASSWORD = "0000";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//    }
//}

