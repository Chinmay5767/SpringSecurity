package com.freelancing_platform.backend.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ValidationFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
	   HttpServletResponse res = (HttpServletResponse) response;
	   String header = req.getHeader("Authorization");
	   if(header!=null) {
	   header = header.trim();
	   if(header.startsWith("Basic ")) {
		   byte[] base64token = header.substring(6).getBytes(StandardCharsets.UTF_8);
		  try {
			  byte[] decoded = Base64.getDecoder().decode(base64token);
			  String token = new String(decoded, StandardCharsets.UTF_8);
			  String[] parts = token.split(":");
				if (parts.length == 1 || parts[0].isEmpty() || parts[1].isEmpty()) {
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				else if (parts.length == 2){
					if(parts[0].contains("test")) {
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					}
				}
			  
		  }
			catch (IllegalArgumentException e) {
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
	   }
	   } else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
	   }
		chain.doFilter(request, response);
	}

}
