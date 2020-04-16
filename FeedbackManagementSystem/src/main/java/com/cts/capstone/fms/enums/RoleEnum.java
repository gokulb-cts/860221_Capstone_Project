package com.cts.capstone.fms.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

	ROLE_ADMIN("Admin Role"), ROLE_PMO("PMO Role"), ROLE_POC("POC Role"), ROLE_PARTICIPANT("Participant Role");

	private String role;

	RoleEnum(String role) {
		this.role = role;
	}

}
