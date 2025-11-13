package com.example.demo.services;

import com.example.demo.dtos.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.repositorise.EmployeeRepository;
import com.example.demo.utility.JwtTOkenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTOkenUtil jwtTOkenUtil;
    private final LocationService loc;
    // CRUD employee
    public void deleteEmployee(Long Id){
        employeeRepository.deleteById(Id);
    }

    public Object create(EmployeeDto request){
        Employee employee=new Employee();
        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employee.setLevel(request.getLevel());
        employee.setToken(jwtTOkenUtil.generateToken(employee.getEmail(),employee.getLevel().toString()));
        employee.setNumber(request.getNumber());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        return Map.of("data",employeeRepository.save(employee),"token",employee.getToken());
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
// token has three part header, payload, signature
//header - never chang ,basic information
// payload - user id, username , expire date, issue data
// signature - secret