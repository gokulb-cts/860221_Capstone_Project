package com.cts.capstone.fms.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "authority")
@Table(name = "authority")
@Getter
@Setter
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorityId;
	
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	@ManyToMany(mappedBy = "authorityList")
	private List<Role> roleList;
	
}
