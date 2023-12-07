package com.example.taskmanager.models;

public class User {
    private int user_id;
    private String name;

    private String email;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return user_id;
    }

    public void setId(int user_id) {
        this.user_id=user_id;
    }
}
