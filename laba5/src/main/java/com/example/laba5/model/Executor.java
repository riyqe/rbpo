package com.example.laba5.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "executor")
public class Executor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String department;
}
