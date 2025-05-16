package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private int age;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
