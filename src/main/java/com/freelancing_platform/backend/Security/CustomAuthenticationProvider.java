package com.freelancing_platform.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.freelancing_platform.backend.service.CustomUserDetailsService;

import jakarta.annotation.Resource.AuthenticationType;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private  CustomUserDetailsService userDetailsService ;
	private  PasswordEncoder passwordEncoder ;
//	public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//		super();
//		this.userDetailsService = userDetailsService;
//		this.passwordEncoder = passwordEncoder;
//	}
	@Autowired
	public void SetUserDetailsService(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Autowired
	public void SetPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		UserDetails userDet = userDetailsService.loadUserByUsername(username);
	    if(passwordEncoder.matches(password, userDet.getPassword())) {
	    	System.out.println("password matched");
	    	return new UsernamePasswordAuthenticationToken(userDet, null, userDet.getAuthorities());
	    } else {
	    	 throw new BadCredentialsException("Invalid credentials");
	    }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
