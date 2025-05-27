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
        super(extractAuthorities(source)); // здесь вызывается extractAuthorities для передачи списка ролей
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

/*    private UserRoleDto extractRole(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) return null;

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
                .map(String::toUpperCase)
                .filter(role -> Arrays.stream(UserRoleDto.values())
                        .anyMatch(enumRole -> enumRole.name().equals(role)))
                .map(UserRoleDto::valueOf)
                .findFirst()
                .orElse(null);
    }*/
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

        // Если у пользователя есть ADMIN — возвращаем только ROLE_ADMIN
        if (roles.contains("ADMIN")) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // Иначе мапим все остальные роли
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList());
    }

/*    private static Collection<? extends GrantedAuthority> extractAuthorities(Jwt jwt) {
        // Пример для извлечения ролей из realm_access
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) {
            return Collections.emptyList();
        }
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");
        // Преобразуем каждую роль в GrantedAuthority с префиксом ROLE_
        return roles.stream()
                .map(String::toUpperCase)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }*/
}
