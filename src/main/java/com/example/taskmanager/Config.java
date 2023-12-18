package com.example.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
//public class Config {
//    Connection conn = null;
//    public static Connection connectdb()
//    {
//        try
//        {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanager","admin","0000");
//            return conn;
//        }
//        catch(Exception e)
//        {
//            return null;
//        }
//    }
//}
public class Config {
    public static final String DB_USER = "admin";
    public static final String DB_PASSWORD = "0000";
    public static final String DB_HOST = "localhost"; // Адрес сервера MySQL
    public static final String DB_PORT = "3306"; // Порт сервера MySQL
    public static final String DB_NAME = "taskmanager"; // Имя вашей базы данных
    public static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

