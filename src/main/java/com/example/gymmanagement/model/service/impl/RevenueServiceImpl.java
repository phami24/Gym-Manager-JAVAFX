package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.entity.MembershipType;
import com.example.gymmanagement.model.entity.Revenue;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.repository.MembershipTypesRepository;
import com.example.gymmanagement.model.repository.RevenueRepository;
import com.example.gymmanagement.model.service.RevenueService;

import java.math.BigDecimal;
import java.util.List;

public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;
    private final MembersRepository membersRepository;
    private final MembershipTypesRepository membershipTypesRepository;

    public RevenueServiceImpl() {
        this.revenueRepository = new RevenueRepository();
        this.membersRepository = new MembersRepository();
        this.membershipTypesRepository = new MembershipTypesRepository();
    }

    @Override
    public void addRevenue(Revenue revenue) {
        revenueRepository.addRevenue(revenue);
    }

    @Override
    public void updateRevenue(Revenue revenue) {
        revenueRepository.updateRevenue(revenue);
    }

    @Override
    public void deleteRevenue(int revenueId) {
        revenueRepository.deleteRevenue(revenueId);
    }

    @Override
    public Revenue getRevenueById(int revenueId) {
        return revenueRepository.getRevenueById(revenueId);
    }

    @Override
    public List<Revenue> getAllRevenue() {
        return revenueRepository.getAllRevenue();
    }
    @Override
    public int getTotalRevenue() {return revenueRepository.getTotalRevenue();}
    @Override
    public BigDecimal calculateTotalRevenueByMonth(int year, int month) {
        List<Revenue> revenues = revenueRepository.getRevenueByYearAndMonth(year, month);
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Revenue revenue : revenues) {
            totalRevenue = totalRevenue.add(revenue.getTotalAmount());
        }

        return totalRevenue;
    }

    @Override
    public BigDecimal calculateTotalRevenueForMemberType(int year, int month, int membershipTypeId) {
        List<Members> membersList = membersRepository.getMembersByYearAndMonth(year, month);
        BigDecimal totalRevenueForMemberType = BigDecimal.ZERO;

        for (Members member : membersList) {
            MembershipType membershipType = membershipTypesRepository.getMembershipTypeById(member.getMembership_type_id());
            if (membershipType != null && membershipType.getMembershipTypeId() == membershipTypeId) {
                totalRevenueForMemberType = totalRevenueForMemberType.add(membershipType.getPrice());
            }
        }

        return totalRevenueForMemberType;
    }
}
