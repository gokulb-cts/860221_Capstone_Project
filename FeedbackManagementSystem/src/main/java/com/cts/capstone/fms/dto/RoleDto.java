package com.cts.capstone.fms.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RoleDto {
	
	private Long roleId;
	
	@NotNull(message = "Role Name is missing/empty")
	private String roleName;
	
	private String roleDescription;
	
	private byte activeStatus;

}
