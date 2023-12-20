package com.example.taskmanager;

import com.example.taskmanager.models.Quote;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class MainController {
//    private ObservableList<String> taskList; // Список задач для отображения в ListView
    ObservableList<Task> taskList = FXCollections.observableArrayList();

    private UserDAO userDAO;
    private User user;
    public MainController() {

    }
    public void initData(User user) {
        this.user = user;
    }
    @FXML
    private Button projectsButton;
    @FXML
    Label Username;
    @FXML
    private Label authorLabel;

    @FXML
    private Label quoteLabel;

//    public MainController(User user) {
//        this.user = user;
//    }
    @FXML
    void initialize() {
        taskList = FXCollections.observableArrayList();
        taskListView.setItems(taskList);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateTaskListForDate(Date.valueOf(newValue),user);
            }
        });
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Title: %s, Status: %s, Description: %s,Date: %s",
                            item.getTitle(), item.getStatus(), item.getDescription(),item.getDate()));
                }
            }
        });
//        if (user != null) {
//            Username.setText("Welcome, " + user.getName());
//        }
//        System.out.println("User Info:");
//        System.out.println("Name: " + user.getName());
//        System.out.println("Email: " + user.getEmail());
        new Thread(() -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.quotable.io/random"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            try {
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                // Parse JSON response using Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                Quote quote = objectMapper.readValue(response.body(), Quote.class);

                // Display author and quote in JavaFX application thread using Platform.runLater
                Platform.runLater(() -> {
                    // Set the text of labels with the extracted data
                    authorLabel.setText("Author: " + quote.getAuthor());
                    quoteLabel.setText("Quote: " + quote.getContent());
                });

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        System.out.println("Метод initialize() вызван");

    }

    private void updateTaskListForDate(Date selectedDate,User user) {
        // Очистите текущий список задач
        taskList.clear();

        // Получите задачи из базы данных для выбранной даты
        // и добавьте их в taskList
        DatabaseHandler dbHandler = new DatabaseHandler();
        List<Task> tasks = dbHandler.loadTasksFromDatabase(selectedDate,user);
        taskList.addAll(tasks);

        // Обновите отображение списка в taskListView
        taskListView.setItems(taskList);
    }
        @FXML
        private TextField titleTextField;

        @FXML
        private TextArea descriptionTextArea;

        @FXML
        private TextField statusTextField;
        @FXML
        private DatePicker datePicker;


        @FXML
            private ListView<Task> taskListView;
//        @FXML
//        private void handleDatePickerAction() {
//            LocalDate selectedDate = datePicker.getValue();
//            updateTaskListForDate(Date.valueOf(selectedDate));
//        }


    //        private void updateTaskListForDate(Date selectedDate) {
//            // Очистите текущий список задач
//            taskList.clear();
//
//            // Получите задачи из базы данных для выбранной даты
//            // и добавьте их в taskList
//            DatabaseHandler dbHandler = new DatabaseHandler();
//            // Напишите код для получения задач по выбранной дате из базы данных
//
//            // Пример (не забудьте адаптировать под вашу структуру данных):
//            List<Task> tasks = dbHandler.loadTasksFromDatabase(selectedDate);
//            taskList.addAll(tasks);
//        }
        @FXML
        private void addTask() {
            DatabaseHandler dbHandler = new DatabaseHandler();
            String title = titleTextField.getText();
            String description = descriptionTextArea.getText();
            Date date = Date.valueOf(datePicker.getValue());
            String status = statusTextField.getText();

            if (!title.isEmpty()) {
                // Создайте объект Task
                Task newTask = new Task(title, status, description, date);

                // Добавьте объект Task в ObservableList
                taskList.add(newTask);

                // Обновите отображение списка в taskListView
                taskListView.setItems(taskList);

                // Сохраните задачу в базе данных
                dbHandler.saveTaskToDatabase(title, description, date, status, user);

                // Очистите поля ввода
                titleTextField.clear();
                descriptionTextArea.clear();
                statusTextField.clear();
                datePicker.getEditor().clear();
            }
        }
        @FXML
        void handleProjectsButton(ActionEvent event) {
            // Load the "Projects.fxml" file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Projects.fxml"));
//                Parent projectsParent = loader.load();
//
//                // Get the controller associated with the "Projects.fxml"
//                ProjectsController projectsController = loader.getController();
//
//                // Pass the user information to the next controller
//                projectsController.setUser(user);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProjectsController projectsController = loader.getController();
            projectsController.initData(user);
            System.out.println(user.getId());
            Scene projectsScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(projectsScene);
            stage.show();

        }

//        @FXML
//        private void addTask() {
//            DatabaseHandler dbHandler = new DatabaseHandler();
//            String title=titleTextField.getText();
//            String description=descriptionTextArea.getText();
//            Date date = Date.valueOf(datePicker.getValue());
//            String status=statusTextField.getText();
//
//
//
//            if (!title.isEmpty()) {
//                String taskEntry = date + ": " + title;
//                taskListView.getItems().add(taskEntry);
//                dbHandler.saveTaskToDatabase(title,description,date,status,user);
//
//                titleTextField.clear();
//            }
//        }
    }

