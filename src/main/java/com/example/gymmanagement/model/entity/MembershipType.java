package com.example.gymmanagement.model.entity;

import java.math.BigDecimal;

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
public class MembershipType {
    private int membershipTypeId;
    private String membershipTypeName;
    private int duration;
    private String description;
    private BigDecimal price;
}
