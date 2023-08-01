package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.repository.InstructorsRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import javafx.collections.ObservableList;

import java.io.IOException;

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
    public void deleteInstructor() throws IOException {
        instructorsRepository.deleteInstructor();
    }

    @Override
    public Instructors getInstructorById(int instructorId) {
        return instructorsRepository.getInstructorById(instructorId);
    }

    @Override
    public ObservableList<Instructors> getAllInstructors() {
        return instructorsRepository.getAllInstructors();
    }
}
