package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Instructors;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface InstructorsService {
    void addInstructor(Instructors instructor);
    void updateInstructor(Instructors instructor);

    void deleteInstructor(int instructorId);
    Instructors getInstructorById(int instructorId);
    List<Instructors> getAllInstructors();
    int getNextMemberID();
}
