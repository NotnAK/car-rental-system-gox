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
                // ============ PUBLIC ============
                .requestMatchers(
                        HttpMethod.GET,
                        "/cars",
                        "/cars/{id}",
                        "/cars/filters",
                        "/cars/{id}/reviews",
                        "/cars/{id}/busy-intervals",
                        "/photos/{id}",
                        "/locations",
                        "/locations/{id}"
                ).permitAll()

                // ============ CUSTOMER ============
                .requestMatchers(
                        HttpMethod.POST,
                        "/cars/{id}/reviews",
                        "/bookings"
                ).hasRole("CUSTOMER")
                .requestMatchers(
                        HttpMethod.GET,
                        "/users/me/reviews",
                        "/users/me/bookings",
                        "/users/me/wishlist"
                ).hasRole("CUSTOMER")
                .requestMatchers(
                        HttpMethod.POST,
                        "/users/me/wishlist/{carId}"
                ).hasRole("CUSTOMER")
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/users/me/wishlist/{carId}"
                ).hasRole("CUSTOMER")
                .requestMatchers(
                        HttpMethod.PUT,
                        "/users/me",
                        "/reviews/{reviewId}"
                ).hasRole("CUSTOMER")

                // ============ CUSTOMER & ADMIN ============
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/reviews/{reviewId}"
                ).hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(
                        HttpMethod.GET,
                        "/users/me",
                        "/bookings/{bookingId}"
                ).hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(
                        HttpMethod.POST,
                        "/bookings/{bookingId}/cancel",
                        "/bookings/estimate"
                ).hasAnyRole("CUSTOMER", "ADMIN")

                // ============ ADMIN ============
                .requestMatchers(
                        HttpMethod.POST,
                        "/cars",
                        "/cars/{id}/photos",
                        "/locations",
                        "/users",
                        "/reviews"
                ).hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.GET,
                        "/users",
                        "/users/{userId}",
                        "/users/{userId}/bookings",
                        "/users/{userId}/reviews",
                        "/bookings",
                        "/reviews"
                ).hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.PUT,
                        "/cars/{id}",
                        "/locations/{locationId}",
                        "/users/{userId}"
                ).hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/cars/{id}",
                        "/photos/{photoId}",
                        "/locations/{locationId}",
                        "/users/{userId}",
                        "/bookings/{bookingId}"
                ).hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.POST,
                        "/bookings/{bookingId}/approve",
                        "/bookings/{bookingId}/complete"
                ).hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.PATCH,
                        "/photos/{photoId}"
                ).hasRole("ADMIN")
                // any other request must be authenticated
                .anyRequest().authenticated();
    }
    private void configureOauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2, JwtDecoder jwtDecoder) {
        oauth2
                .jwt(jwt -> {
                    jwt.decoder(jwtDecoder);
                    jwt.jwtAuthenticationConverter(JwtConverter::new);
                });
    }
}
