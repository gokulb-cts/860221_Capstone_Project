package com.cts.capstone.fms.security;

import static com.cts.capstone.fms.security.constants.SecurityConstants.LOG_IN_URL;
import static com.cts.capstone.fms.security.constants.SecurityConstants.SIGN_UP_URL;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.security.constants.SecurityConstants;
import com.cts.capstone.fms.service.FmsUserService;

import lombok.AllArgsConstructor;

/** To Authorize and Authenticate requests **/

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@AllArgsConstructor
public class FmsWebSecurity extends WebSecurityConfigurerAdapter {

	private final FmsUserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final FmsUserRepository userRepository;
	
	// For authentication service to authenticate request
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService) /**
												 * service that implements UserDetailService which implements
												 * loadByUserName() where DB username and password are retrieved
												 **/
				.passwordEncoder(bCryptPasswordEncoder); /**
															 * type of encoder used in order to encrypt incoming request
															 * password and compare with encrypted password from db
															 **/
	}

	// Requests that need to be handled for authorization and authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // Disable csrf for REST API
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll() // permit without authentication
				.antMatchers(HttpMethod.POST, SecurityConstants.FEEDBACK_URL).permitAll()
				.antMatchers(HttpMethod.GET, SecurityConstants.FEEDBACK_URL).permitAll()
				.anyRequest() // for any other request
				.authenticated() // do authentication
				.and().addFilter(getAuthenticaitonFilter()) // Add Authentication Filter
				.addFilter(new AuthorizationFilter(authenticationManager(), userRepository)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	public AuthenticationFilter getAuthenticaitonFilter() throws Exception {

		final AuthenticationFilter authFilter = new AuthenticationFilter(authenticationManager());
		authFilter.setFilterProcessesUrl(LOG_IN_URL);
		return authFilter;

	}

}
