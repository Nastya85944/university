package com.example.demo.service;

import com.example.demo.entities.Student;
import com.example.demo.entities.User;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> findAllUsers();

    User updateUser(User user, int userId);

    User findUserById(int userId);

    void deleteUser(int userId);
}
