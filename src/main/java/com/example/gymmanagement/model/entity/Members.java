package com.example.gymmanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor // Generates an all-args constructor
public class Members extends Person {
    private int member_id;
    private String join_date;
    private String end_date;
    private int membership_status_id;
    private int membership_type_id;
    private int instructorId;
}

