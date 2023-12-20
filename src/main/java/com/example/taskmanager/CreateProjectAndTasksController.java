package com.example.taskmanager;

import com.example.taskmanager.models.Project;
import com.example.taskmanager.models.ProjectTask;
import com.example.taskmanager.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.util.List;
public class CreateProjectAndTasksController {
    private User user;
    private Project selectedProject; // declare selectedProject as a class member

    public void initData(User user) {
        this.user = user;
        initialize(user);
        System.out.println("User ID: " + user.getId());
    }
    @FXML
    TextField projecttitle;
    @FXML
    TextField projectdescription;
    @FXML
    TextField tasktitle;
    @FXML
    TextField taskdescription;
    @FXML
    ChoiceBox<Project> projectchoice;
    private ObservableList<Project> projectList;

    @FXML
    ChoiceBox<String> statuschoice;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    public void initialize(User user) {
        ObservableList<String> statusOptions = FXCollections.observableArrayList("zu erledigen", "in Bearbeitung", "erledigt");
        statuschoice.setItems(statusOptions);
        DatabaseHandler dbHandler = new DatabaseHandler();

        List<Project> projects = dbHandler.loadProjectsFromDatabase(user);
        projectchoice.setItems(FXCollections.observableArrayList(projects));
        projectchoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedProject = newValue;
        });
    }
    @FXML
    private void addProject() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String title = projecttitle.getText();
        String description = projectdescription.getText();
        if (!title.isEmpty()) {
            Project newProject = new Project(title, description);
            dbHandler.saveProjectToDatabase(title, description,user);
            projecttitle.clear();
            projectdescription.clear();
        }
    }
    @FXML
    private void addProjectTask(ActionEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        selectedProject = projectchoice.getSelectionModel().getSelectedItem();


        if (selectedProject != null) {
            // Create a new project task
            ProjectTask newProjectTask = new ProjectTask();
            newProjectTask.setTitle(tasktitle.getText());
            newProjectTask.setDescription(taskdescription.getText());
            newProjectTask.setDate_start(Date.valueOf(startDate.getValue()));
            newProjectTask.setDate_end(Date.valueOf(endDate.getValue()));
            newProjectTask.setStatus(statuschoice.getValue());
            newProjectTask.setProject(selectedProject);

            // Save the new task to the database
            dbHandler.saveProjectTaskToDatabase(newProjectTask);

            // Print debug information
            System.out.println("Selected Project: " + selectedProject);
            System.out.println("Task Title: " + newProjectTask.getTitle());
            System.out.println("Task Description: " + newProjectTask.getDescription());
            System.out.println("Task status: " + newProjectTask.getStatus());
            System.out.println("Task start date: " + newProjectTask.getDate_start());
            System.out.println("Task end date: " + newProjectTask.getDate_end());

            // Clear input fields
            tasktitle.clear();
            taskdescription.clear();
        } else {
            System.out.println("Ошибка: Выберите проект");
        }
    }

//    @FXML
//    private void addProjectTask(ActionEvent event){
//        DatabaseHandler dbHandler = new DatabaseHandler();
//        List<Project> projects = dbHandler.loadProjectsFromDatabase(user);
//        // Populate the choice box with projects
//        projectList = FXCollections.observableArrayList(projects);
//        projectchoice.setItems(projectList);
//        Project selectedProject = projectchoice.getSelectionModel().getSelectedItem();
//
//        if (selectedProject != null) {
//            ProjectTask newProjectTask = new ProjectTask();
//            newProjectTask.setTitle(tasktitle.getText());
//            newProjectTask.setDescription(taskdescription.getText());
//            newProjectTask.setDate_start(Date.valueOf(startDate.getValue()));
//            newProjectTask.setDate_end(Date.valueOf(endDate.getValue()));
//            newProjectTask.setStatus(statuschoice.getValue());
//            newProjectTask.setProject(selectedProject);
//
//            // Save the new task to the database
//            dbHandler.saveProjectTaskToDatabase(newProjectTask);
//            System.out.println("Selected Project: " + selectedProject);
//            System.out.println("Task Title: " + newProjectTask.getTitle());
//            System.out.println("Task Description: " + newProjectTask.getDescription());
//            System.out.println("Task status: " + newProjectTask.getStatus());
//            System.out.println("Task start date: " + newProjectTask.getDate_start());
//            System.out.println("Task end date: " + newProjectTask.getDate_end());
//
//            tasktitle.clear();
//            taskdescription.clear();
//
//        }else {
//            System.out.println("oshibka");
//        }
//        String title = tasktitle.getText();
//        String description = taskdescription.getText();
//        Date startdate=Date.valueOf(startDate.getValue());
//        Date enddate=Date.valueOf(endDate.getValue());
//        String status=statuschoice.getValue();
//        if (!title.isEmpty()) {
//            ProjectTask newProjectTask = new ProjectTask(title,status, description,startdate,enddate);
//            dbHandler.saveProjectToDatabase(title, description,user);
//            projecttitle.clear();
//            projectdescription.clear();
//        }
    }

