package com.example.queue1.repo;

import com.example.queue1.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByUsername(String username);

}
