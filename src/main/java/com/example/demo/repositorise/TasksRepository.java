package com.example.demo.repositorise;

import com.example.demo.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Tasks,Long> {
}
