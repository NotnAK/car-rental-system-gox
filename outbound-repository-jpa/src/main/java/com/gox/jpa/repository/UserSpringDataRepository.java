package com.gox.jpa.repository;

import com.gox.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSpringDataRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByOrderByIdDesc();
}
