package com.cts.capstone.fms.enums;

public enum Authority {

	ADMIN("ROLE_ADMIN"), PMO("ROLE_PMO"), POC("ROLE_POC");

	private String authority;

	Authority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

}
