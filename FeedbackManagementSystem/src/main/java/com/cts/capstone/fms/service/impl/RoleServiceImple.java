package com.cts.capstone.fms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.repositories.RoleRepository;
import com.cts.capstone.fms.service.RoleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoleServiceImple implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Flux<Role> getAllRoles() {
		return Flux.fromIterable(roleRepository.findAll());
	}

	@Override
	public Mono<Role> getRoleByRoleId(Long roleId) {
		return Mono.justOrEmpty(roleRepository.findById(roleId));
	}

	@Override
	public Mono<Role> getRoleByRoleName(String roleName) {
		return Mono.justOrEmpty(roleRepository.findByRoleNameIgnoreCase(roleName));
	}

	@Override
	public Mono<Role> saveRole(Role role) {
		return Mono.justOrEmpty(roleRepository.save(role));
	}

}
