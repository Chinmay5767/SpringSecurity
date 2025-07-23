package com.freelancing_platform.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.freelancing_platform.backend.entity.UserAuthEntity;
import com.freelancing_platform.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{


	 @Autowired
	 private UserRepository userRepository;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		UserAuthEntity LoginUser = (UserAuthEntity) userRepository.findByUserName(username);
	 return new org.springframework.security.core.userdetails.User(LoginUser.getUsername(), LoginUser.getPassword(),
			 List.of(new SimpleGrantedAuthority("ROLE_USER")));
	
	}
	 
	
}
