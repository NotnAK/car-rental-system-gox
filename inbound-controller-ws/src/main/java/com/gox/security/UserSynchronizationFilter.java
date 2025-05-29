package com.gox.security;

import com.gox.domain.entity.user.LoyaltyLevel;
import com.gox.domain.entity.user.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.gox.domain.entity.user.User;
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
            User user = userFacade.getByEmail(principal.getEmail());
            if (user == null) {
                User newUser = new User();
                newUser.setUsername(principal.getUsername());
                newUser.setEmail(principal.getEmail());
                newUser.setName(principal.getName());
                if (principal.getRole() != null) {
                    newUser.setRole(UserRole.valueOf(principal.getRole().name()));
                } else {
                    newUser.setRole(UserRole.CUSTOMER);
                }
                newUser.setLoyaltyLevel(LoyaltyLevel.STANDARD);
                if(principal.getAddress()!=null){
                    newUser.setAddress(principal.getAddress());
                }
                if(principal.getPhone()!=null){
                    newUser.setPhone(principal.getPhone());
                }
                userFacade.create(newUser);
            }
        }
        filterChain.doFilter(request, response);
    }
}
