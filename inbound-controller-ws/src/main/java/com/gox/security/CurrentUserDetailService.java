package com.gox.security;

import com.gox.domain.entity.user.User;
import com.gox.domain.service.UserFacade;
import com.gox.rest.dto.UserShortDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserDetailService {

    private final UserFacade userFacade;

    public CurrentUserDetailService(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserShortDto getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserShortDto userShort) {
            return userShort;
        }
        throw new IllegalStateException("Current user is not of type UserShortDto.");
    }

    public String getUserEmail() {
        return getCurrentUser().getEmail();
    }

    public User getFullCurrentUser() {
        return userFacade.getByEmail(getUserEmail());
    }
    public Optional<UserShortDto> getOptionalUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserShortDto userShort) {
            return Optional.of(userShort);
        }
        return Optional.empty();
    }

    public Optional<User> getOptionalFullUser() {
        return getOptionalUser()
                .map(UserShortDto::getEmail)
                .map(userFacade::getByEmail);
    }

}
