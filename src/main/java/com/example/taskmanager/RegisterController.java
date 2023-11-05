package com.example.taskmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    Button Register;
    @FXML
    TextField name;
    @FXML
    PasswordField password;
    @FXML
    TextField email;
    @FXML
    void initialize() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Register.setOnAction(event -> {
            try {
                dbHandler.signUpUser(name.getText(), email.getText(), password.getText());
                openLoginWindow(event);
            } catch (DatabaseHandler.RegistrationException e) {
                showAlert("Registration Failed", "Please try again");
            }
        });
    }

    private void openLoginWindow(ActionEvent event) {
        try {
            // Загружаем FXML файл для окна логина
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Создаем новую сцену
            Scene scene = new Scene(root);

            // Получаем текущий стейдж и устанавливаем на нем новую сцену
            Stage stage = (Stage) Register.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
