package com.example.taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
//    public static void main(String[] args) {
//        Connection connection = null;
//        try {
//            connection = Config.getConnection();
//            if (connection != null) {
//                System.out.println("Successfully connected to the database.");
//
//                Statement statement = connection.createStatement();
//                String sql = "SELECT * FROM test";
//                ResultSet resultSet = statement.executeQuery(sql);
//
//                while (resultSet.next()) {
//                    String data = resultSet.getString("name");
//                    String data1 = resultSet.getString("surname");
//                    System.out.println(data);
//                    System.out.println(data1);
//
//                }
//
//                resultSet.close();
//                statement.close();
//            } else {
//                System.err.println("Failed to connect to the database.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        launch();
//
//    }
}