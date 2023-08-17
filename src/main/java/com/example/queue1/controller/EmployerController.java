package com.example.queue1.controller;

import com.example.queue1.entity.Task;
import com.example.queue1.service.TaskQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employer")
public class EmployerController {

    private final TaskQueueService taskQueueService;

    @PostMapping("/addTask")
    public void addTask(@RequestBody Task task) {
        task.setStatus("Pending");
        taskQueueService.addTask(task);
    }

    @GetMapping("/viewTasks")
    public List<Task> getAllTasks() {
        return taskQueueService.getAllTasks();
    }
}

