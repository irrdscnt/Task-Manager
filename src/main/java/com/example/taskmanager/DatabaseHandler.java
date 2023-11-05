package com.example.taskmanager;

import java.sql.*;

public class DatabaseHandler {
    private Connection connection;

    public Connection getDbConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw e;
        }
    }
    public void createUserTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS User ("
                + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255), "
                + "password VARCHAR(255), "
                + "email VARCHAR(255) "
                + ")";

        try (Statement statement = getDbConnection().createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createTaskTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Task ("
                + "task_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255), "
                + "description TEXT, "
                + "date DATE, "
                + "status VARCHAR(255)"
                + ")";

        try (Statement statement = getDbConnection().createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String email,String password){
        String selectQuery = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_EMAIL + " = ? AND " + Const.USERS_PASSWORD + " = ?";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(selectQuery)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void signUpUser(String name,String password,String email) throws RegistrationException{
        String insert= "INSERT INTO "+ Const.USERS_TABLE + "("+Const.USERS_NAME + "," + Const.USERS_EMAIL+ "," +Const.USERS_PASSWORD+ ")" + "VALUES(?,?,?)";

        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(insert);
            prSt.setString(1,name);
            prSt.setString(2,password);
            prSt.setString(3,email);
            int rowsAffected = prSt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RegistrationException("Registration failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public class RegistrationException extends Exception {
        public RegistrationException(String message) {
            super(message);
        }
    }

    public void closeDbConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void executeQuery(String sql) throws SQLException {
        try (Connection connection = getDbConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw e;
        }
    }
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.createTaskTable();
        dbHandler.createUserTable();

    }

}

