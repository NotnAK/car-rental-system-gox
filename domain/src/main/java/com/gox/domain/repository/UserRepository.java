package com.gox.domain.repository;

import com.gox.domain.entity.user.User;
import java.util.List;

public interface UserRepository {
    User read(Long id);
    User findByEmail(String email);
    User create(User user);
    User update(User user);
    List<User> findAll();
    void delete(Long id);
}
