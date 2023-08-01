package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.repository.InstructorsRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import javafx.collections.ObservableList;


import java.util.List;

public class InstructorsServiceImpl implements InstructorsService {
    private final InstructorsRepository instructorsRepository;

    public InstructorsServiceImpl() {
        this.instructorsRepository = new InstructorsRepository();
    }

    @Override
    public void addInstructor(Instructors instructor) {
        instructorsRepository.addInstructor(instructor);
    }

    @Override
    public void updateInstructor(Instructors instructor) {
        instructorsRepository.updateInstructor(instructor);
    }

    @Override
    public void deleteInstructor(int instructorId) {
        instructorsRepository.deleteInstructor(instructorId);
    }


    @Override
    public Instructors getInstructorById(int instructorId) {
        return instructorsRepository.getInstructorById(instructorId);
    }

    @Override
    public List<Instructors> getAllInstructors() {
        return instructorsRepository.getAllInstructors();
    }

    @Override
    public int getNextMemberID() {
        return instructorsRepository.getNextMemberID();
    }
}
