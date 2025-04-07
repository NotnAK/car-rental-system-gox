package com.gox.domain.repository;

import com.gox.domain.entity.User;
import java.util.List;

public interface UserRepository {
    User readByEmail(String email);
    User create(User user);
    List<User> findAll();
}
