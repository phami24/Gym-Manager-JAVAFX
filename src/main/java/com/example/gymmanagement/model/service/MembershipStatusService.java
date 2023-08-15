package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.MembershipStatus;
import com.example.gymmanagement.model.repository.MembershipStatusRepository;

import java.util.List;

public interface MembershipStatusService {

    public List<MembershipStatus> getAllMembershipStatus();
}
