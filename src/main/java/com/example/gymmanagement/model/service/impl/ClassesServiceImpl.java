package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Classes;
import com.example.gymmanagement.model.repository.ClassesRepository;
import com.example.gymmanagement.model.service.ClassesService;

import java.util.List;

public class ClassesServiceImpl implements ClassesService {
    private final ClassesRepository classesRepository;

    public ClassesServiceImpl() {
        this.classesRepository = new ClassesRepository();
    }

    @Override
    public void addClass(Classes classes) {
        classesRepository.addClass(classes);
    }

    @Override
    public void updateClass(Classes classes) {
        classesRepository.updateClass(classes);
    }

    @Override
    public void deleteClass(int classId) {
        classesRepository.deleteClass(classId);
    }

    @Override
    public Classes getClassById(int classId) {
        return classesRepository.getClassById(classId);
    }

    @Override
    public List<Classes> getAllClasses() {
        return classesRepository.getAllClasses();
    }

    @Override
    public int getTotalClasses(){return classesRepository.getTotalClasses();}
}
