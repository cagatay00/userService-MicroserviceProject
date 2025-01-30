package com.example.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable csrf
                .csrf(AbstractHttpConfigurer::disable)

                // Configure authorization rules with lambdas
                .authorizeHttpRequests(auth -> auth
                        // Let anyone call the register,login endpoint
                        .requestMatchers("/api/users/register", "/api/users/login",
                                "/api/users/profile", "/api/users/signout", "/api/users/change-password" ).permitAll()

                        // Require authentication for the signout endpoint
                        //.requestMatchers().authenticated()

                        // Everything else requires authentication
                        .anyRequest().authenticated()) //.formLogin(login -> login.disable()) // disable default login form
                .httpBasic(AbstractHttpConfigurer::disable); // Disable basic auth


        // Build and return the SecurityFilterChain
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
