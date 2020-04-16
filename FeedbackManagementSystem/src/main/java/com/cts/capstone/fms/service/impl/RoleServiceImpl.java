package com.cts.capstone.fms.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.dto.RoleDto;
import com.cts.capstone.fms.repositories.RoleRepository;
import com.cts.capstone.fms.service.RoleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoleServiceImpl implements RoleService {

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
	public Mono<Role> saveRole(RoleDto roleDto) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Role role = modelMapper.map(roleDto, Role.class);
		return Mono.justOrEmpty(roleRepository.save(role));
	}

	@Override
	public Mono<Role> updateRole(Long roleId, RoleDto roleDto) {
		
		Optional<Role> roleOp = roleRepository.findById(roleId);
		
		if(roleOp.isPresent()) {
			Role role = roleOp.get();
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			role = modelMapper.map(roleDto, Role.class);
			return Mono.just(roleRepository.save(role));
		}
		return Mono.empty();
	}

	@Override
	public void deleteRole(Long roleId) {
		Optional<Role> roleOp = roleRepository.findById(roleId);
		if (!roleOp.isPresent()) {
			throw new RuntimeException("Role does not exists with role ID: " + roleId);
		}
		roleRepository.delete(roleOp.get());

	}
}
