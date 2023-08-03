package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Instructors;

import java.math.BigDecimal;
import java.util.List;

public interface InstructorsService {
    void addInstructor(Instructors instructor);
    void updateInstructor(Instructors instructor);
    void deleteInstructor(int instructorId);
    Instructors getInstructorById(int instructorId);
    List<Instructors> getAllInstructors();
    BigDecimal calculateSalary(int instructorId);
    int getNumberOfClassesByInstructorId(int instructorId);
}
