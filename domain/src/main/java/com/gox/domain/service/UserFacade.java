package com.gox.domain.service;

import com.gox.domain.entity.User;

import java.util.List;

public interface UserFacade {
    User getByEmail(String email);
    List<User> getAllUsers();
}
