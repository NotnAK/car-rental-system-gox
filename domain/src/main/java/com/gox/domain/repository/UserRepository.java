package com.gox.domain.repository;

import com.gox.domain.entity.User;
import java.util.List;

public interface UserRepository {
    User readByEmail(String email);
    List<User> findAll();
}
