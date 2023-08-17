package com.example.queue1.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Worker {
    private String username;
    private String password;
    private List<Task> assignedTasks;

    public Worker(String username, String password) {
        this.username = username;
        this.password = password;
        this.assignedTasks = new ArrayList<>();
    }
}

