package com.example.queue1.service;

import com.example.queue1.entity.Task;
import com.example.queue1.entity.Worker;
import com.example.queue1.repo.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TaskQueueService {

    private static long id = 0;
    private final WorkerRepository workerRepository;
    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    public TaskQueueService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public void addTask(Task task) {
        task.setId(id++);
        taskQueue.add(task);
    }

    public Task getNextTaskForWorker(String worker) {

        for (Task task : taskQueue) {
            if (worker.equals(task.getAssignedWorker())) {
                task.setStatus("In Progress");
                return task;
            }
        }


        for (Task task : taskQueue) {
            if (task.getAssignedWorker() == null) {
                task.assignWorker(worker);
                task.setStatus("In Progress");
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
                    task.getStatus().equals("In Progress") &&
                    task.getId() == taskId) {
                Worker byUsername = workerRepository.findByUsername(worker);
                byUsername.getAssignedTasks().remove(task);
                taskQueue.remove(task);
                return true;
            }
        }

        return false;
    }
}
