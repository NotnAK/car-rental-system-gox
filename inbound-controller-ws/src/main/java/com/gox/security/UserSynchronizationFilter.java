package com.gox.security;

import com.gox.domain.entity.LoyaltyLevel;
import com.gox.domain.entity.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.gox.domain.entity.User;
import com.gox.domain.service.UserFacade;
import com.gox.rest.dto.UserShortDto;

@Component
public class UserSynchronizationFilter extends OncePerRequestFilter {

    private final UserFacade userFacade;

    public UserSynchronizationFilter(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserShortDto) {
            UserShortDto principal = (UserShortDto) authentication.getPrincipal();
            // Check if the user exists in the local database
            User user = userFacade.getByEmail(principal.getEmail());
            if (user == null) {
                // If the user is not found - create a new one
                User newUser = new User();
                newUser.setEmail(principal.getEmail());
                newUser.setName(principal.getName());
                newUser.setRole(UserRole.valueOf(principal.getRole().name()));
                newUser.setLoyaltyLevel(LoyaltyLevel.STANDARD);
                userFacade.create(newUser);
            }
        }
        filterChain.doFilter(request, response);
    }
}
