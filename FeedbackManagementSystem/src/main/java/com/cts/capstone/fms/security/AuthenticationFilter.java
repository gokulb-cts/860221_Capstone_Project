package com.cts.capstone.fms.security;

import static com.cts.capstone.fms.security.constants.SecurityConstants.EXPIRATION_TIME;
import static com.cts.capstone.fms.security.constants.SecurityConstants.HEADER_STRING;
import static com.cts.capstone.fms.security.constants.SecurityConstants.TOKEN_SECRET;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.capstone.fms.dto.FmsUserLoginDto;
import com.cts.capstone.fms.security.constants.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

/*** To Authenticate Username and Password from request 
 * which triggers loadByUserName() of UserDetailsService***/

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	//Authentication checker
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
															throws AuthenticationException {
		
		try {
			//Convert incoming request stream to User Login Model
			FmsUserLoginDto userLoginDto = 
					new ObjectMapper().readValue(request.getInputStream(), FmsUserLoginDto.class);
			
			//look up information from db, using loadByUserName()
			return authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
									userLoginDto.getUserId(), 
									userLoginDto.getPassword(),
									new ArrayList<>()));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//On Successful Authentication - triggered after attemptAuthentication()
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		
		String userName = ((UserPrincipal) auth.getPrincipal()).getUsername();
		
		String token = Jwts.builder()
				.setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
				.compact();
		
		response.addHeader(HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
	}
		
	
}
