package com.example.gymmanagement.model.service.impl;

import com.example.gymmanagement.model.entity.Users;
import com.example.gymmanagement.model.repository.UserRepository;
import com.example.gymmanagement.model.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepository();
    }

    @Override
    public void addUser(Users user) {
        userRepository.addUser(user);
    }

    @Override
    public void updateUser(Users user) {
        userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public Users getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
