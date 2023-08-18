package com.example.queue1.service;

import com.example.queue1.entity.Status;
import com.example.queue1.entity.Task;
import com.example.queue1.entity.Worker;
import com.example.queue1.repo.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class TaskQueueService {

    private static long id = 0;
    private final WorkerRepository workerRepository;
    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();


    public Task addTask(Task task) {
        task.setId(id++);
        taskQueue.add(task);
        return task;
    }

    public Task getNextTaskForWorker(String worker) {
        for (Task task : taskQueue) {
            if (worker.equals(task.getAssignedWorker())) {
                task.setStatus(Status.IN_PROGRESS);
                return task;
            } else if (task.getAssignedWorker() == null) {
                task.assignWorker(worker);
                task.setStatus(Status.IN_PROGRESS);
                return task;
            }
        }
        return null;
    }


    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>(taskQueue);
        return allTasks;
    }

    public boolean completeTask(String worker, long taskId) {
        for (Task task : taskQueue) {
            if (task.getAssignedWorker() != null &&
                    task.getAssignedWorker().equals(worker) &&
                    task.getStatus() == Status.IN_PROGRESS &&
                    task.getId() == taskId) {
                Optional<Worker> byUsername = workerRepository.findByUsername(worker);
                byUsername.ifPresent(worker1 -> worker1.getAssignedTasks().remove(task));
                taskQueue.remove(task);
                return true;
            }
        }
        return false;
    }
}
