package com.example.gymmanagement.model.entity;

import javafx.scene.control.Button;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor
public class Instructors extends Person {
    private int instructor_id;
    private String hire_date;
    private String specialization;
    private int experienceYears;
}