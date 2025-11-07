package com.example.demo.dtos;


import com.example.demo.entities.Employee;
import lombok.Data;

@Data
public class EmployeeDto {
    private Long Id;

    private String name;

    private String number;
    private String email;
    private Employee.Role level;

    private String password;
}
