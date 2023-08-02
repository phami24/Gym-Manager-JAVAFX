package com.example.gymmanagement.model.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor // Generates an all-args constructor
@NoArgsConstructor // Generates a no-args constructor
public abstract class Person {
    private int person_id;
    private String first_name;
    private String last_name;
    private String dob;
    private String gender;
    private String email;
    private String phone_number;
    private String address;
}
