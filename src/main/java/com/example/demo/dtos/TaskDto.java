package com.example.demo.dtos;


import lombok.Data;

@Data // using this annotation we donn't need to write getter setter hashCode and toString methods
public class TaskDto {
    private Long id;

    private String desc;
    private boolean complete;
}
