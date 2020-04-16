package com.cts.capstone.fms.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FmsUserLoginDto {
	
	@NotNull(message = "User ID is empty/missing")
	private String userId;
	
	@NotNull(message = "Password is empty/missing")
	private String password;
	
}
