package com.example.queue1.auth.service;

import com.example.queue1.auth.entity.JwtUserDetails;
import com.example.queue1.entity.Worker;
import com.example.queue1.repo.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final WorkerRepository workerRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Worker worker = workerRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        return new JwtUserDetails(worker.getId(), username, worker.getPassword(), roles);
    }

}
