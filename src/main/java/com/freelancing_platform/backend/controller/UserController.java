package com.freelancing_platform.backend.controller;
import org.springframework.security.core.Authentication;

import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.freelancing_platform.backend.entity.UserAuthEntity;
import com.freelancing_platform.backend.repository.UserRepository;


import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	private  AuthenticationManager authenticationManager;
	
	 
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> userRegister(@RequestBody UserAuthEntity user) {
		System.out.println(user.getEmailID()+ " " + user.getUsername());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
		if(userRepository.existsByEmailID(user.getEmailID())) 
			return ResponseEntity.badRequest().body(Map.of("message","user is already registered"));
		else 
		{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			System.out.println("registered succcessful");
			return ResponseEntity.ok(Map.of("message","user saved successfully"));
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserAuthEntity user , HttpServletRequest http){
		try {
          Authentication authentication = authenticationManager.authenticate(
        		  new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
          
          return ResponseEntity.ok(Map.of("message", "user logged in successfully"));
          // if login failed then authenticate function returns AuthenticationException
          
		}
		catch (Exception e) {
			return	 ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid username or password"));
		}
		
	}
	
	
		
	
}
