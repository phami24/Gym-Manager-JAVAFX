package com.example.gymmanagement.model.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class Admin {
    private int adminId;
    private String username;
    private String password;

}