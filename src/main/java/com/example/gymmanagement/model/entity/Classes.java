package com.example.gymmanagement.model.entity;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class Classes {
    private int class_id;
    private String class_name;
    private int instructor_id;
    private String schedule;
    private int capacity;
}
