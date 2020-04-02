package com.cts.capstone.fms.DTO;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Table("user")
@Entity
@Data
public class FmsUser {
	@Id
	private Long id;
	private Long userId;
	private String userName;
	@JsonIgnore
	private String password;
	private String emailId;
	private String mobileNumber;
	private byte activeStatus;
	
	@OneToMany
	@JoinTable(name = "user_role",
				joinColumns = @JoinColumn(name = "userId"),
				inverseJoinColumns = @JoinColumn(name = "roleId"))
	private List<Role> roles;
}
