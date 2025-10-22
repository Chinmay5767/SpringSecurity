package com.freelancing_platform.backend;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.freelancing_platform.backend.Filter.CsrfTokenFilter;
import com.freelancing_platform.backend.Filter.ValidationFilter;
import com.freelancing_platform.backend.Security.CustomAuthenticationProvider;
import com.freelancing_platform.backend.service.CustomUserDetailsService;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;



import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity


public class SecurityConfig {
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
	CustomUserDetailsService userDetailsService;
    @Autowired
    CsrfTokenRequestHandler csrfTokenRequestHandler;
	
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	
	return http.securityContext(scc -> scc.requireExplicitSave(false))
			// it tells security context that i not storing security context details and logged in authentication details inside security context holder
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.csrfTokenRequestHandler(csrfTokenRequestHandler)
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			// with this configuration we are telling security to store csrf token on cookies and we need withHttpOnlyFalse because we want to access it from javascript
	        .addFilterAfter(new CsrfTokenFilter(), BasicAuthenticationFilter.class)
	        .addFilterBefore(new ValidationFilter(), BasicAuthenticationFilter.class)
			.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(1).maxSessionsPreventsLogin(true))
			.authorizeHttpRequests(
					request -> request.requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
							.anyRequest().authenticated())
			.formLogin(form -> form.disable())
			.httpBasic(Customizer.withDefaults())
			.logout(loc -> loc.clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID"))
			.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


//@Bean 
//public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//	AuthenticationManagerBuilder authManager = http.getSharedObject(AuthenticationManagerBuilder.class);
//	
//	
//	 authManager.authenticationProvider(customAuthenticationProvider)
//			.userDetailsService(userDetailsService)
//			.passwordEncoder(passwordEncoder());
//	
//	return authManager.build();
//			
//	
//}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    // Spring automatically picks up all AuthenticationProviders
    return configuration.getAuthenticationManager();
}
@Bean
public UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));  // Allow all headers
    configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));
    configuration.setAllowCredentials(true);  // if you need cookies/JSESSIONID
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

@Bean
public CsrfTokenRequestHandler csrfTokenRequestHandler() {
  return new XorCsrfTokenRequestAttributeHandler();
}
	
}
