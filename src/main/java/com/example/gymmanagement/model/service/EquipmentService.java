package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    void addEquipment(Equipment equipment);
    void updateEquipment(Equipment equipment);
    void deleteEquipment(int equipmentId);
    Equipment getEquipmentById(int equipmentId);
    List<Equipment> getAllEquipment();

    int getTotalEquipment();
}
