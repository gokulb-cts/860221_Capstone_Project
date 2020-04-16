package com.cts.capstone.fms.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")	
@Getter
@Setter
@JsonIgnoreProperties(value = {"createdBy","createdDate","modifiedBy","lastModifiedDate","activeStatus", "authorityList"}, allowSetters = true)
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	@Column(nullable = false)
	private String roleName;
	
	private String roleDescription;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long modifiedBy;
	
	private Date lastModifiedDate;
	
	private byte activeStatus = 1;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "role_authority", joinColumns = @JoinColumn(name = "role_id"),
										inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private List<Authority> authorityList;

}
