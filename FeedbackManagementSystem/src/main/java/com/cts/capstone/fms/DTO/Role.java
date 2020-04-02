package com.cts.capstone.fms.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "role")
@Entity
@Data
public class Role {
	@Id
	private Long roleId;
	private String roleName;
	private String roleDescription;
	private byte activeStatus;
}
