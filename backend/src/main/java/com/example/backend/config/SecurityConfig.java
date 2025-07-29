package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/businesses/signup",
                                "/api/businesses/login",
                                "/api/businesses/**",
                                "/api/investors/signup",
                                "/api/investors/login",
                                "/api/investors/**",
                                "/api/saved/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
