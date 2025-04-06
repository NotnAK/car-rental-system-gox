package com.gox.domain.service;

import com.gox.domain.entity.User;
import com.gox.domain.repository.UserRepository;

import java.util.List;

public class UserService implements UserFacade{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.readByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
