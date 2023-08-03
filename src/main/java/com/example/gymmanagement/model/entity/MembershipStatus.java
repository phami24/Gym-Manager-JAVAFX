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
public class MembershipStatus {
    private int membershipStatusId;
    private String membershipStatusName;
}
