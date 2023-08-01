package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Instructors;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface InstructorsService {
    void addInstructor(Instructors instructor);
    void updateInstructor(Instructors instructor);
    void deleteInstructor() throws IOException;
    Instructors getInstructorById(int instructorId);
    ObservableList<Instructors> getAllInstructors();
}
