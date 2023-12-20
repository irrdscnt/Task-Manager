package com.example.taskmanager.models;

import java.sql.Date;

public class ProjectTask {
    private Project project;
    private  int ptask_id;
    private String title;

    private String description;

    private Date date_start;
    private Date date_end;
    private String status;


    public ProjectTask(int ptask_id,String title, String status, String description) {
        this.ptask_id = ptask_id;
        this.title = title;
        this.status = status;
        this.description = description;
    }
    public ProjectTask() {
        // Default constructor logic (if needed)
    }
//        this.date_start=date_start;
//        this.date_end=date_end;
//    public ProjectTask(String title,String description, String status, Date date_start,Date date_end,Project project) {
//        this.project=project;
//        this.title = title;
//        this.status = status;
//        this.description = description;
//        this.date_start=date_start;
//        this.date_end=date_end;
//    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


}
