package com.gox.domain.service;

import com.gox.domain.entity.User;
import com.gox.domain.exception.UserServiceException;
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

    @Override
    public User create(User user) {
        validateUser(user);
        return userRepository.create(user);
    }
    private void validateUser(User user) {
        if (user == null) {
            throw new UserServiceException("User object must not be null.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new UserServiceException("User email must not be empty.");
        }

        if (userRepository.readByEmail(user.getEmail()) != null) {
            throw new UserServiceException("User with email '" + user.getEmail() + "' already exists.");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new UserServiceException("User name must not be empty.");
        }

        if (user.getRole() == null) {
            throw new UserServiceException("User role must be specified.");
        }
    }
}
