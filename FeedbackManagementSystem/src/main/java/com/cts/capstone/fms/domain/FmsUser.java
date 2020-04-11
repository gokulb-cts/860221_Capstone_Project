package com.cts.capstone.fms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
//@JsonIgnoreProperties(value = { "encryptedPassword", "createdBy", "createdDate" }, allowSetters = true)
public class FmsUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private Long userId;

	@Column(nullable = false)
	private String userName;

	private String emailId;

	private String encryptedPassword;

	private String mobileNumber;

	private Long createdBy;

	private Date createdDate;

	@Column(columnDefinition = "int default 1")
	private byte activeStatus;

	@OneToOne
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Role role;
}
