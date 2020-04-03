package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.RoleConstants.ROLE_END_POINT;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.service.RoleService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class RoleRestController {
	
	@Autowired
	public RoleService roleService; 
	
	@GetMapping(value= ROLE_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Role> getAllRoles() {
		log.info("getAllRoles()");
		return roleService.getAllRoles();
	}
	
	@GetMapping(value = ROLE_END_POINT + "/{roleId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE )
	public Mono<Role> getRoleByRoleId(@PathVariable Long roleId) {
		log.info("getRoleByRoleId()");
		return roleService.getRoleByRoleId(roleId);
	}
		
	@GetMapping(value = ROLE_END_POINT + "/{roleName}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE )
	public Mono<Role> getRoleByRoleName(@PathVariable String roleName) {
		log.info("getRoleByRoleName()");
		return roleService.getRoleByRoleName(roleName);
	}
	
	@PostMapping(value = ROLE_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE ) 
	public Mono<ResponseEntity<Object>> addRole(@RequestBody Role role) {
		log.info("addRole()" + role);
		ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
		
		return roleService.saveRole(role)
						   .map(savedRole -> 
				   					{
										URI location = 
												uriBuilder
												.path("/{roleId}")
												.buildAndExpand(savedRole.getRoleId())
												.toUri();
										return ResponseEntity.created(location).build();
									})
						   .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
}
