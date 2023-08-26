package com.example.gymmanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Long transactionId;
    private Long memberId;
    private Long equipmentId; // Add this line
    private String transactionType;
    private String transactionDate;
    private BigDecimal amount;
}
