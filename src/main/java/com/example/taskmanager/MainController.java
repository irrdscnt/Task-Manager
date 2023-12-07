package com.example.taskmanager;

import com.example.taskmanager.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainController {
    private ObservableList<String> taskList; // Список задач для отображения в ListView

    private UserDAO userDAO;
    private User user;
    public MainController() {

    }
    public void initData(User user) {
        this.user = user;
        // Дополнительные операции с пользователем при необходимости
    }
    @FXML
    Label Username;

//    public MainController(User user) {
//        this.user = user;
//    }
    @FXML
    void initialize() {
        taskList = FXCollections.observableArrayList();
        taskListView.setItems(taskList);
//        if (user != null) {
//            Username.setText("Welcome, " + user.getName());
//        }
//        System.out.println("User Info:");
//        System.out.println("Name: " + user.getName());
//        System.out.println("Email: " + user.getEmail());

        System.out.println("Метод initialize() вызван");

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
        private ListView<String> taskListView;
        @FXML
        private void handleDatePickerAction() {
            LocalDate selectedDate = datePicker.getValue();
            updateTaskListForDate(Date.valueOf(selectedDate));
        }
        private void updateTaskListForDate(Date selectedDate) {
            // Очистите текущий список задач
            taskList.clear();

            // Получите задачи из базы данных для выбранной даты
            // и добавьте их в taskList
            DatabaseHandler dbHandler = new DatabaseHandler();
            // Напишите код для получения задач по выбранной дате из базы данных

            // Пример (не забудьте адаптировать под вашу структуру данных):
            List<String> tasks = dbHandler.loadTasksFromDatabase(selectedDate);
            taskList.addAll(tasks);
        }

        @FXML
        private void addTask() {
            DatabaseHandler dbHandler = new DatabaseHandler();
            String title=titleTextField.getText();
            String description=descriptionTextArea.getText();
            Date date = Date.valueOf(datePicker.getValue());
            String status=statusTextField.getText();



            if (!title.isEmpty()) {
                String taskEntry = date + ": " + title;
                taskListView.getItems().add(taskEntry);
                dbHandler.saveTaskToDatabase(title,description,date,status,user);

                titleTextField.clear();
            }
        }
    }

