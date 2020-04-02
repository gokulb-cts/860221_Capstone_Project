package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByRoleName(String roleName);
	
}
