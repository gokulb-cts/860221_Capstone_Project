package com.cts.capstone.fms.enums;

import lombok.Getter;

@Getter
public enum AuthorityEnum {

	READ_AUTHORITY("Read Authority"), 
	WRITE_AUTHORITY("Write Authority"),
	DELETE_AUTHORITY("Delete Authority");
	
	private String authority;
	
	AuthorityEnum(String authority) {
		this.authority =  authority;
	}
	
}
