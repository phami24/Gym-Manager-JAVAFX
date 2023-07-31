package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.service.MembersService;

import java.util.List;

public class MembersServiceImpl implements MembersService {
    private final MembersRepository membersRepository;

    public MembersServiceImpl() {
        this.membersRepository = new MembersRepository();
    }

    @Override
    public void addMember(Members member) {
        membersRepository.addMember(member);
    }

    @Override
    public void updateMember(Members member) {
        membersRepository.updateMember(member);
    }

    @Override
    public void deleteMember(int memberId) {
        membersRepository.deleteMember(memberId);
    }

    @Override
    public Members getMemberById(int memberId) {
        return membersRepository.getMemberById(memberId);
    }

    @Override
    public List<Members> getAllMembers() {
        return membersRepository.getAllMembers();
    }
}
