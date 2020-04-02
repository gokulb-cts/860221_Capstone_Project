package com.cts.capstone.fms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.DTO.FmsUser;
import com.cts.capstone.fms.DTO.Role;

@Repository
public interface FmsUserRepository extends JpaRepository<FmsUser, Long>{
	
	public FmsUser findByUserId(Long userId); 
	
	public FmsUser findByEmailId(String emailId);
	
	public List<FmsUser> findByRole(Role role);
	
}
