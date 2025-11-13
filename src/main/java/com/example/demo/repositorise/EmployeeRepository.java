package com.example.demo.repositorise;

import com.example.demo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByNumberContaining(String num);
    Optional<Employee> findByToken(String token);

    Optional<Employee> findByEmail(String username);
}
