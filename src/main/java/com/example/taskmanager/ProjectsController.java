package com.example.taskmanager;

import com.example.taskmanager.models.Project;
import com.example.taskmanager.models.ProjectTask;
import com.example.taskmanager.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ProjectsController {
    private User user;

    public void initData(User user) {
        this.user = user;
        initializeProjects();
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ChoiceBox<Project> projectChoiceBox;

    @FXML
    private HBox todoBox;

    @FXML
    private HBox inProgressBox;

    @FXML
    private HBox finishedBox;

    private ObservableList<Project> projectList;

//    public void CreateProjectClick(ActionEvent event){
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("createproject.fxml"));
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        CreateProjectAndTasksController CreateProjectController = loader.getController();
//        CreateProjectController.initData(user);
//        System.out.println(user.getId());
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//    }
    public void CreateProjectClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createproject.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CreateProjectAndTasksController createProjectController = loader.getController();

        // Check if user is not null before passing it to initData
        if (user != null) {
            createProjectController.initData(user);
            System.out.println(user.getId());
        } else {
            System.out.println("USER ID IS NULL");
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void initializeProjects() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        projectList = FXCollections.observableArrayList(dbHandler.loadProjectsFromDatabase(user));
        projectChoiceBox.setItems(projectList);

        // Set up ChoiceBox and button event handlers
        projectChoiceBox.setOnAction(event -> handleProjectSelection());
    }

    private void handleProjectSelection() {
        Project selectedProject = projectChoiceBox.getValue();

        if (selectedProject != null) {
            todoBox.getChildren().clear();
            inProgressBox.getChildren().clear();
            finishedBox.getChildren().clear();

            for (ProjectTask task : selectedProject.getTasks()) {
                if ("zu erledigen".equals(task.getStatus())) {
                    todoBox.getChildren().add(createTaskLabel(task.getDescription()));
                } else if ("in Bearbeitung".equals(task.getStatus())) {
                    inProgressBox.getChildren().add(createTaskLabel(task.getDescription()));
                } else if ("erledigt".equals(task.getStatus())) {
                    finishedBox.getChildren().add(createTaskLabel(task.getDescription()));
                }
            }
        }
    }

    private Label createTaskLabel(String taskText) {
        Label taskLabel = new Label(taskText);
        // Additional styling and settings for the task label
        return taskLabel;
    }

}

