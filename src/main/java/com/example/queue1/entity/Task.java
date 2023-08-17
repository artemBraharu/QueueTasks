package com.example.queue1.entity;

import lombok.Data;

@Data
public class Task {

    private long id;
    private String description;
    private String assignedWorker;
    private String status;

    public void assignWorker(String worker) {
        this.assignedWorker = worker;
    }
}
