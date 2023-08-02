package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Equipment;
import com.example.gymmanagement.model.repository.EquipmentRepository;
import com.example.gymmanagement.model.service.EquipmentService;

import java.util.List;

public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl() {
        this.equipmentRepository = new EquipmentRepository();
    }

    @Override
    public void addEquipment(Equipment equipment) {
        equipmentRepository.addEquipment(equipment);
    }

    @Override
    public void updateEquipment(Equipment equipment) {
        equipmentRepository.updateEquipment(equipment);
    }

    @Override
    public void deleteEquipment(int equipmentId) {
        equipmentRepository.deleteEquipment(equipmentId);
    }

    @Override
    public Equipment getEquipmentById(int equipmentId) {
        return equipmentRepository.getEquipmentById(equipmentId);
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.getAllEquipment();
    }
}
