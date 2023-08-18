package com.example.queue1.auth.controller;

import com.example.queue1.entity.Worker;
import com.example.queue1.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final WorkerService workerService;

    @PostMapping("/register")
    public Worker register(@RequestBody Worker worker) {
        Worker saved = workerService.saveWorker(worker);
        return saved;
    }
}
