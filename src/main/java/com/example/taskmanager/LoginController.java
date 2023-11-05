package com.example.taskmanager;

import com.example.taskmanager.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import com.example.taskmanager.Config;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;


import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button Login;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Email;
    @FXML
    private Button Register;

    @FXML
    void loginAction(ActionEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String email = Email.getText();
        String password = Password.getText();

        User user = dbHandler.loginUser(email, password);
        if (user != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            MainController mainController = new MainController(user);
            loader.setController(mainController);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            showAlert("Login Failed", "Please check your credentials and try again.");
        }

    }

//    private void openMainMenu(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
//            MainController mainController = new MainController(user);
//            loader.setController(mainController);
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    @FXML
//    void loginAction() {
//        DatabaseHandler dbHandler = new DatabaseHandler();
//        Login.setOnAction(event -> {
//            String email = Email.getText();
//            String password = Password.getText();
//
//            if (dbHandler.loginUser(email, password)) {
//                Scene scene = null;
//                try {
//                    scene = new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml")));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                dialogStage.setScene(scene);
//                dialogStage.show();
//                } else {
//                showAlert("Login Failed", "Please check your credentials and try again.");
//            }
//        });
//    }
@FXML
public void registerClick(ActionEvent event) {
    Parent root;
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/taskmanager/register.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Закрываем текущее окно (если нужно)
        ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

//    @FXML
//    public void registerClick(){
//        Register.setOnAction(event -> {
//            Register.getScene().getWindow().hide();
//            FXMLLoader loader= new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com/example/taskmanager/register.fxml"));
//            try {
//                loader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Parent root = loader.getRoot();
//            Stage stage= new Stage();
//            stage.setScene(new Scene(root));
//            stage.showAndWait();
//        });
//
//    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

}
