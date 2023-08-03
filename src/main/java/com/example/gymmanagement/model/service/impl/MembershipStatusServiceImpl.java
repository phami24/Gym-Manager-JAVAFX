package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.MembershipStatus;
import com.example.gymmanagement.model.repository.MembershipStatusRepository;
import com.example.gymmanagement.model.service.MembershipStatusService;

import java.util.List;

public class MembershipStatusServiceImpl implements MembershipStatusService {

    private final MembershipStatusRepository membershipStatusRepository;

    public MembershipStatusServiceImpl() {
        this.membershipStatusRepository = new MembershipStatusRepository();
    }

    public List<MembershipStatus> getAllMembershipStatus() {
        return membershipStatusRepository.getAllMembershipStatus();
    }
}
