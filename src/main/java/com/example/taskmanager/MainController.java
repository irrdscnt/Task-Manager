package com.example.taskmanager;

import com.example.taskmanager.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {
    private UserDAO userDAO;
    private User user; // Добавьте поле user
    public MainController() {
        // Пустой конструктор без аргументов
    }

    @FXML
    Label Username;

    public MainController(User user) {
        this.user = user;
    }
    @FXML
    void initialize() {

        if (user != null) {
            Username.setText("Welcome, " + user.getName());
        }
        System.out.println("User Info:");
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());

        System.out.println("Метод initialize() вызван");

    }


}
