package com.example.gymmanagement.model.entity;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class Equipment {
    private int equipmentId;
    private String equipmentName;
    private String category;
    private String purchaseDate;
    private BigDecimal price;
    private String status;
    private String notes;
}