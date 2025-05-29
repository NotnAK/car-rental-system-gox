package com.gox.domain.service;
import com.gox.domain.entity.user.User;

import java.util.List;

public interface UserFacade {
    User get(Long id);
    User getByEmail(String email);
    User create(User user);
    List<User> getAllUsers();
    void delete(Long id);
    List<Long> getWishlistCarIds(Long userId);
    User updateProfile(User user, String name, String phone, String address);
    User updateByAdmin(Long userId, String name, String phone, String address, String loyaltyLevel);
}
