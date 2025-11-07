package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(unique = true,updatable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role level;

    @JsonIgnore
    private String token;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToMany
    private List<Task> tasks;

    public enum Role{
        A,
        B,
        C
    }

}
