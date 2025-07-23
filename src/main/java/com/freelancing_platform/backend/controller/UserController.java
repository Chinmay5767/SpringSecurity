package com.freelancing_platform.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.freelancing_platform.backend.entity.UserAuthEntity;
import com.freelancing_platform.backend.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	 @Autowired
	 private UserRepository userRepository;
	 
	@PostMapping("/register")
	public ResponseEntity<String> userRegister(@RequestBody UserAuthEntity user) {
		System.out.println(user.getEmailID()+ " " + user.getUsername());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
		if(userRepository.existsByEmailID(user.getEmailID())) 
			return ResponseEntity.badRequest().body("user is already registered");
		else 
		{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return ResponseEntity.ok("user saved successfully");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserAuthEntity user , HttpServletRequest http){
		try {
		http.login(user.getUsername(), user.getPassword());
		return ResponseEntity.ok("Login successful");
		}
		catch (ServletException e) {
			return	ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
		
	}
	
	
		
	
}
