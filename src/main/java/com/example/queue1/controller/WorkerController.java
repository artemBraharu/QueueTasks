package com.example.queue1.controller;

import com.example.queue1.entity.Task;
import com.example.queue1.entity.Worker;
import com.example.queue1.repo.WorkerRepository;
import com.example.queue1.service.TaskQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Worker byUsername = workerRepository.findByUsername(worker);
        if (byUsername == null) {
            return null;
        }
        Task nextTaskForWorker = taskQueueService.getNextTaskForWorker(worker);
        byUsername.getAssignedTasks().add(nextTaskForWorker);
        return nextTaskForWorker;
    }

    @GetMapping("/{worker}/active-tasks")
    public ResponseEntity<List<Task>> getActiveTasksForWorker(@PathVariable String worker) {
        Worker byUsername = workerRepository.findByUsername(worker);
        if (byUsername == null) {
            return null;
        }
        List<Task> assignedTasks = byUsername.getAssignedTasks();
        return ResponseEntity.ok(assignedTasks);
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeTask(@RequestParam String worker, @RequestParam long taskId) {
        Worker byUsername = workerRepository.findByUsername(worker);
        if (byUsername == null) {
            return null;
        }

        boolean success = taskQueueService.completeTask(worker, taskId);
        if (success) {
            return ResponseEntity.ok("Task marked as completed and removed from queue.");
        } else {
            return ResponseEntity.badRequest().body("Failed to complete task or task not found.");
        }
    }

    @PostMapping("/login")
    public void login(@RequestBody Worker worker) {
        Task nextTaskForWorker = taskQueueService.getNextTaskForWorker(worker.getUsername());
        worker.getAssignedTasks().add(nextTaskForWorker);
        workerRepository.save(worker);
    }

}

