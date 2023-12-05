package com.example.taskmanager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskManager extends Application {

    private DatePicker datePicker;
    private TextArea taskTextArea;
    private ListView<String> taskListView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Manager");

        // Календарь
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        // Текстовое поле для ввода задачи
        taskTextArea = new TextArea();
        taskTextArea.setWrapText(true);

        // Кнопка добавления задачи
        Button addButton = new Button("Добавить");
        addButton.setOnAction(event -> addTask());

        // Список задач
        taskListView = new ListView<>();

        // Расположение элементов на экране
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Дата:"), 0, 0);
        gridPane.add(datePicker, 1, 0);
        gridPane.add(new Label("Задача:"), 0, 1);
        gridPane.add(taskTextArea, 1, 1);
        gridPane.add(addButton, 1, 2);
        gridPane.add(new Label("Список задач:"), 0, 3);
        gridPane.add(taskListView, 1, 3);

        // Обработчик выбора даты для отображения задач на конкретную дату
        datePicker.setOnAction(event -> showTasks());

        // Создание основной сцены
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addTask() {
        String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String task = taskTextArea.getText();

        if (!task.isEmpty()) {
            String taskEntry = date + ": " + task;
            taskListView.getItems().add(taskEntry);
            taskTextArea.clear();
        }
    }

    private void showTasks() {
        taskListView.getItems().clear();
        // TODO: Здесь можно реализовать отображение задач для выбранной даты из списка
    }
}
