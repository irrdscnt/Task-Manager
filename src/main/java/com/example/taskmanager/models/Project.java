package com.example.taskmanager.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Project {
    private int project_id;
    String title;
    String description;
    private List<ProjectTask> tasks;

    @Override
    public String toString() {
        return getTitle();
    }
    public int getProject_id() {
        return project_id;
    }
    public String getTitle(){
        return title;
    }
    public Project(int project_id,String title){
        this.project_id=project_id;
        this.title = title;
        this.tasks = new ArrayList<>();

    }
    public Project(String title,String description){
        this.title = title;
        this.description=description;
    }
    public void addTask(ProjectTask task) {
        tasks.add(task);
    }
    public List<ProjectTask> getTasks() {

        return tasks;
    }
//    public List<ProjectTask> getTasks(int projectId) {
//        return tasks.stream()
//                .filter(task -> task.getProject_id() == projectId)
//                .collect(Collectors.toList());
//    }
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }


}
