package com.cts.capstone.fms.security.constants;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 86400000; //1 day in milliseconds
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/api/v1/users";
	public static final String TOKEN_SECRET = "hq0wi7bnx0pe7yu";
	public static final String LOG_IN_URL = "/api/v1/users/login";
}
