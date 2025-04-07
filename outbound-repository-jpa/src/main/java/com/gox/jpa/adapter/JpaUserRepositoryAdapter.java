package com.gox.jpa.adapter;

import com.gox.domain.entity.User;
import com.gox.domain.repository.UserRepository;
import com.gox.jpa.repository.UserSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JpaUserRepositoryAdapter implements UserRepository {
    private final UserSpringDataRepository userSpringDataRepository;

    public JpaUserRepositoryAdapter(UserSpringDataRepository userSpringDataRepository) {
        this.userSpringDataRepository = userSpringDataRepository;
    }

    @Override
    public User readByEmail(String email) {
        return userSpringDataRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userSpringDataRepository.findAll();
    }

    @Override
    public User create(User user) {
        return userSpringDataRepository.save(user);
    }
}
