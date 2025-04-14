package com.gox.domain.service;

import com.gox.domain.entity.user.User;

import java.util.List;

public interface UserFacade {
    User getByEmail(String email);
    User create(User user);
    List<User> getAllUsers();
}
