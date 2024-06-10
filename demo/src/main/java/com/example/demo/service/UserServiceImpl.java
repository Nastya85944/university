package com.example.demo.service;

import com.example.demo.entities.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User updateUser(User user, int userId) {
        User existingUser = findUserById(userId);
        existingUser.setUserName(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setCreatedAt(user.getCreatedAt());
        existingUser.setUpdatedAt(user.getUpdatedAt());
        return usersRepository.save(existingUser);
    }

    @Override
    public User findUserById(int userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by " + userId));
    }

    @Override
    public void deleteUser(int userId) {
        User user = findUserById(userId);
        usersRepository.deleteById(user.getId());
    }
}
