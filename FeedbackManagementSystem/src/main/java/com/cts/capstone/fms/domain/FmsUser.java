package com.cts.capstone.fms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Table(name = "user")
@Entity
@Data
@JsonIgnoreProperties(value = {"password","createdBy","createdDate"}, allowSetters = true)
public class FmsUser implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private String userName;
	private String password;
	private String emailId;
	private String mobileNumber;
	private Long createdBy;
	private Date createdDate;
	private byte activeStatus;

	@OneToOne
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Role role;
}
