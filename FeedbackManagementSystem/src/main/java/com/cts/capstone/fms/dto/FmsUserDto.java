package com.cts.capstone.fms.dto;

import lombok.Data;

@Data
public class FmsUserDto {
	
	private Long userId;
		
	private String userName;
	
	private String emailId;
	
	private String password;
	
	private String mobileNumber;
	
}
