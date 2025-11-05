package com.example.demo.services;

import com.example.demo.dtos.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.repositorise.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public void deleteEmployee(Long Id){
        employeeRepository.deleteById(Id);
    }

    public Employee create(EmployeeDto request){
        Employee employee=new Employee();
        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employee.setNumber(request.getNumber());
        employee.setPassword(request.getPassword());
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Long Id, EmployeeDto request){
        Employee exist = employeeRepository.findById(Id).orElseThrow(()->new RuntimeException("Employee is not found with id "+ Id));
        exist.setId(request.getId());
        exist.setEmail(request.getEmail());
        exist.setName(request.getName());
        exist.setNumber(request.getNumber());
        exist.setPassword(request.getPassword());
        return employeeRepository.save(exist);
    }

}
