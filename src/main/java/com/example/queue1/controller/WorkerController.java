package com.example.queue1.controller;

import com.example.queue1.entity.Task;
import com.example.queue1.entity.Worker;
import com.example.queue1.repo.WorkerRepository;
import com.example.queue1.service.TaskQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private final TaskQueueService taskQueueService;
    private final WorkerRepository workerRepository;


    public WorkerController(TaskQueueService taskQueueService, WorkerRepository workerRepository) {
        this.taskQueueService = taskQueueService;
        this.workerRepository = workerRepository;
    }

    @GetMapping("/getNextTask")
    public Task getNextTask(@RequestParam String worker) {
        Optional<Worker> byUsername = workerRepository.findByUsername(worker);
        if (byUsername.isEmpty()) {
            return null;
        }
        Task nextTaskForWorker = taskQueueService.getNextTaskForWorker(worker);
        byUsername.get().getAssignedTasks().add(nextTaskForWorker);
        return nextTaskForWorker;
    }

    @GetMapping("/{worker}/active-tasks")
    public ResponseEntity<List<Task>> getActiveTasksForWorker(@PathVariable String worker) {
        Optional<Worker> byUsername = workerRepository.findByUsername(worker);
        if (byUsername.isEmpty()) {
            return null;
        }
        List<Task> assignedTasks = byUsername.get().getAssignedTasks();
        return ResponseEntity.ok(assignedTasks);
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeTask(@RequestParam String worker, @RequestParam long taskId) {
        Optional<Worker> byUsername = workerRepository.findByUsername(worker);
        if (byUsername.isEmpty()) {
            return null;
        }
        boolean success = taskQueueService.completeTask(worker, taskId);
        if (success) {
            return ResponseEntity.ok("Task marked as completed and removed from queue.");
        } else {
            return ResponseEntity.badRequest().body("Failed to complete task or task not found.");
        }
    }
}

