package com.cts.capstone.fms.security;

import static com.cts.capstone.fms.security.constants.SecurityConstants.HEADER_STRING;
import static com.cts.capstone.fms.security.constants.SecurityConstants.TOKEN_PREFIX;
import static com.cts.capstone.fms.security.constants.SecurityConstants.TOKEN_SECRET;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//Get Authorization Header
		String header = request.getHeader(HEADER_STRING);
		
		//if Authorization header is not available or doesnt have token prefix bearer, do nothing and return
		if(header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		//If bearer token present, get UsernamePasswordAuthenticationToken obj from the request token
		UsernamePasswordAuthenticationToken auth = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		chain.doFilter(request, response);
		
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if(token != null) {
			
			token = token.replace(TOKEN_PREFIX, "");
			
			String user = Jwts.parser()
							  .setSigningKey(TOKEN_SECRET)
							  .parseClaimsJws(token)
							  .getBody()
							  .getSubject();
			
			if(user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
		}
		return null;
	}
	
	

}
