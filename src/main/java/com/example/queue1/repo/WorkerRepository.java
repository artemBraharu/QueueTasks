package com.example.queue1.repo;

import com.example.queue1.entity.Worker;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class WorkerRepository {
    private final Map<String, Worker> workerMap = new HashMap<>();

    public void save(Worker worker) {
        workerMap.put(worker.getUsername(), worker);
    }


    public Worker findByUsername(String username) {
        return workerMap.get(username);
    }
}
