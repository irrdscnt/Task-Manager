package com.example.taskmanager.models;

import java.sql.Date;

public class Task {
    private String title;

    private String description;

    private Date date;
    private String status;
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public Date getDate() {
        return date;
    }


    public Task(String title, String status, String description,Date date) {
        this.title = title;
        this.status = status;
        this.description = description;
        this.date=date;
    }
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

}
