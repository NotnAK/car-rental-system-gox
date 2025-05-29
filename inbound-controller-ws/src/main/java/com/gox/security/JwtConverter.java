package com.gox.security;

import com.gox.rest.dto.UserRoleDto;
import com.gox.rest.dto.UserShortDto;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class JwtConverter extends AbstractAuthenticationToken {

    private final Jwt source;

    public JwtConverter(Jwt source) {
        super(extractAuthorities(source));
        this.source = Objects.requireNonNull(source);
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return Collections.emptyList();
    }

    @Override
    public Object getPrincipal() {
        UserShortDto user = new UserShortDto();
        user.setUsername(source.getClaimAsString("preferred_username"));
        user.setEmail(source.getClaimAsString("email"));
        user.setName(source.getClaimAsString("name"));
        user.setRole(extractRole(source));
        user.setAddress(source.getClaimAsString("address"));
        user.setPhone(source.getClaimAsString("phone"));
        return user;
    }

    private UserRoleDto extractRole(Jwt jwt) {
        List<String> roles = Optional.ofNullable(jwt.getClaimAsMap("realm_access"))
                .map(m -> (List<String>) m.get("roles"))
                .orElse(Collections.emptyList())
                .stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        if (roles.contains("ADMIN")) {
            return UserRoleDto.ADMIN;
        }
        if (roles.contains("CUSTOMER")) {
            return UserRoleDto.CUSTOMER;
        }
        return null;
    }
    private static Collection<? extends GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> roles = Optional.ofNullable(jwt.getClaimAsMap("realm_access"))
                .map(m -> (List<String>) m.get("roles"))
                .orElse(Collections.emptyList())
                .stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        if (roles.contains("ADMIN")) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList());
    }

}
