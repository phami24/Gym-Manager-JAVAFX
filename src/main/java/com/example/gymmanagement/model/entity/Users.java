package com.example.gymmanagement.model.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class Users {
    private int user_id;
    private String username;
    private String password;
    private int role_id;
}
