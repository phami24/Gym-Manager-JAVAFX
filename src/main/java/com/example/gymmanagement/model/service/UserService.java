package com.example.gymmanagement.model.service;

import com.example.gymmanagement.model.entity.Users;

import java.util.List;

public interface UserService {
    void addUser(Users user);
    void updateUser(Users user);
    void deleteUser(int userId);
    Users getUserById(int userId);
    List<Users> getAllUsers();
}
