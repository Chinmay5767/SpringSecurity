package com.freelancing_platform.backend;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;


import jakarta.persistence.Entity;


@Configuration
@EnableWebSecurity


public class SecurityConfig {

@Bean

public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	
	return http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(request -> request.anyRequest().authenticated())
			.formLogin(request -> request.disable())
			.httpBasic(request -> request.disable())
			.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


@Bean 
public AuthenticationManager authenticationManager() {
	
	return null;
	
}
	
}
