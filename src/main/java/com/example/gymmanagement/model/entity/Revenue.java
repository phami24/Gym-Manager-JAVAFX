package com.example.gymmanagement.model.entity;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class Revenue {
    private int revenueId;
    private int year;
    private int month;
    private BigDecimal totalAmount;
}