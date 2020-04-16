package com.cts.capstone.fms.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FmsUserRegisterDto {
	
	@NotNull(message = "User ID is empty/missing")
	private Long userId;
		
	@NotNull(message = "Name is empty/missing")
	private String userName;
	
	@NotNull(message = "EmailID is empty/missing")	
	private String emailId;
	
	@NotNull(message = "Password is empty/missing")	
	private String password;
	
	private String mobileNumber;
	
}
