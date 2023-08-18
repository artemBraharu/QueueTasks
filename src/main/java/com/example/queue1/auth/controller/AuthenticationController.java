package com.example.queue1.auth.controller;

import com.example.queue1.auth.service.JwtTokenService;
import com.example.queue1.auth.service.JwtUserDetailsService;
import com.example.queue1.auth.entity.AuthenticationRequest;
import com.example.queue1.auth.entity.AuthenticationResponse;
import com.example.queue1.entity.Task;
import com.example.queue1.entity.Worker;
import com.example.queue1.service.TaskQueueService;
import com.example.queue1.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenService jwtTokenService;
    private final TaskQueueService taskQueueService;
    private final WorkerService workerService;

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody final AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken(userDetails));

        //giving the worker the first task when logging in
        Optional<Worker> byUsername = workerService.findByUsername(authenticationRequest.getLogin());
        Task nextTaskForWorker = taskQueueService.getNextTaskForWorker(authenticationRequest.getLogin());
        byUsername.ifPresent(worker -> {
            worker.getAssignedTasks().add(nextTaskForWorker);
            workerService.saveWorker(worker);
        });
        return authenticationResponse;
    }

}
