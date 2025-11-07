package com.example.demo.repositorise;

import com.example.demo.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Task,Long> {
}
