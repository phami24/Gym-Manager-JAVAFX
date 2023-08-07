package com.example.gymmanagement.model.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor
public class Instructors extends Person {
    private int instructor_id;
    private String hireDate;
    private int experienceYears;
    private BigDecimal baseSalary;
    private BigDecimal salary;
}