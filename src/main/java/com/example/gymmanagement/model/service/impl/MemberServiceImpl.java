package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.service.MembersService;

import java.util.List;

public class MemberServiceImpl implements MembersService {
    private final MembersRepository membersRepository;

    public MemberServiceImpl() {
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
        membersRepository.deleteMemberByStatus(memberId,4);
    }

    @Override
    public Members getMemberById(int memberId) {
        return membersRepository.getMemberById(memberId);
    }

    @Override
    public List<Members> getAllMembers() {
        return membersRepository.getAllMembers();
    }

    @Override
    public List<Members> getMembersByNames(String firstName, String lastName) {
        return membersRepository.getMembersByNames(firstName, lastName);
    }

    @Override
    public int getNumberOfMembersByInstructorId(int instructorId) {
        return membersRepository.getMembersByInstructorId(instructorId).size();
    }

    @Override
    public int getTotalMembers() {
        return membersRepository.getTotalMembers();
    }
    @Override
    public List<Members> searchMembersByName(String name) {
        return membersRepository.searchMembersByName(name);
    }
}
