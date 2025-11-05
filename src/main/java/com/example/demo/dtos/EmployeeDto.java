package com.example.demo.dtos;


import lombok.Data;

@Data
public class EmployeeDto {
    private Long Id;

    private String name;

    private String number;
    private String email;

    private String password;
}
