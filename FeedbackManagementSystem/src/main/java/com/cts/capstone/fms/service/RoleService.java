package com.cts.capstone.fms.service;

import com.cts.capstone.fms.domain.Role;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {

	Flux<Role> getAllRoles();

	Mono<Role> getRoleByRoleId(Long roleId);
	
	Mono<Role> getRoleByRoleName(String roleName);

	Mono<Role> saveRole(Role role);

}
