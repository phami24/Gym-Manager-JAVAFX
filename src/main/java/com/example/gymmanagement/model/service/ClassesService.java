package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Classes;

import java.util.List;

public interface ClassesService {
    void addClass(Classes classes);
    void updateClass(Classes classes);
    void deleteClass(int classId);
    Classes getClassById(int classId);
    List<Classes> getAllClasses();
    int getTotalClasses();
}
