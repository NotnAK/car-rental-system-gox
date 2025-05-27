package com.gox.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configureAuthorizationRules)
                .oauth2ResourceServer(oauth2 -> configureOauth2ResourceServer(oauth2, jwtDecoder))
                .build();
    }

    private void configureAuthorizationRules(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        // ============ PUBLIC (ALL) ============
        auth
                .requestMatchers(HttpMethod.GET, "/cars", "/cars/*", "/cars/*/reviews", "/photos/*",
                        "/cars/*/busy-intervals", "/locations").permitAll();

        // ============ ADMIN only ============
        auth
                .requestMatchers(HttpMethod.POST, "/cars").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/cars/*/photos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN");

        // ============ CUSTOMER only ============
        auth
                // оставить возможность админу тоже, если нужно: .hasAnyRole("CUSTOMER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/cars/*/reviews").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/bookings/estimate").permitAll()
                .requestMatchers(HttpMethod.PUT, "/reviews/*").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/me").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/me/wishlist/*").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.DELETE, "/users/me/wishlist/*").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/users/me/reviews").hasRole("CUSTOMER");

        // ============ CUSTOMER or ADMIN ============
        auth
                .requestMatchers(HttpMethod.DELETE, "/reviews/*").hasAnyRole("CUSTOMER","ADMIN");

        // всё остальное — запретить или разрешить анонимно по вкусу:
        auth.anyRequest().authenticated();
    }
    private void configureOauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2, JwtDecoder jwtDecoder) {
        oauth2
                .jwt(jwt -> {
                    jwt.decoder(jwtDecoder);
                    jwt.jwtAuthenticationConverter(JwtConverter::new);
                });
    }
}
