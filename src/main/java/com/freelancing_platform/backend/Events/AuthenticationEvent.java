package com.freelancing_platform.backend.Events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvent {
	
	
	@EventListener
	public void OnAuthenticationSuccess(AuthenticationSuccessEvent event) {
		
		System.out.println("authentication successful for user: " + event.getAuthentication().getName());
	}
	
	@EventListener
	public void OnAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
		System.out.println("authentication failed for user: " + event.getAuthentication().getName()+ " due to " + event.getException().getMessage());
	}
    
}
