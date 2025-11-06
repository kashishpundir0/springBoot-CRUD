package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)

    private String name;

    private String number;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany
    private List<Tasks> tasks;

}
