package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Revenue;

import java.math.BigDecimal;
import java.util.List;

public interface RevenueService {
    void addRevenue(Revenue revenue);

    void updateRevenue(Revenue revenue);

    void deleteRevenue(int revenueId);

    Revenue getRevenueById(int revenueId);

    List<Revenue> getAllRevenue();

    BigDecimal calculateTotalRevenueByMonth(int year, int month);

    BigDecimal calculateTotalRevenueForMemberType(int year, int month, int membershipTypeId);
    BigDecimal getTotalRevenue();
}
