package com.cts.capstone.fms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "role")	
@Data
@JsonIgnoreProperties(value = {"createdBy","createdDate","modifiedBy","lastModifiedDate"}, allowSetters = true)
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	@NotNull(message = "Role Name is missing/empty")
	private String roleName;
	
	private String roleDescription;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long modifiedBy;
	
	private Date lastModifiedDate;
	
	private byte activeStatus;

}
