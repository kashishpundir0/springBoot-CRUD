package com.example.demo.controller;

import com.example.demo.dtos.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @DeleteMapping("/delete/{Id}") // to delete the data
     public ResponseEntity<?> deleteUser(@PathVariable Long Id){
         employeeService.deleteEmployee(Id);
        return ResponseEntity.ok("Employee Deleted");
    }

    @PostMapping("/create") // used to create new data
    public ResponseEntity<?> createUser(@ModelAttribute EmployeeDto request){
        return ResponseEntity.ok(employeeService.create(request));
    }

    @GetMapping("/all")  // used for fetch data or read operation
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PutMapping("/update/{Id}")  //used to update the data
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long Id, @RequestBody EmployeeDto request){
        return ResponseEntity.ok(employeeService.updateEmployee(Id, request));
    }



}