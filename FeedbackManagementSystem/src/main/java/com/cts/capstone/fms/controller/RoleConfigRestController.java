package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.RoleConstants.ROLE_END_POINT;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.dto.RoleDto;
import com.cts.capstone.fms.service.RoleService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class RoleConfigRestController {
	
	@Autowired
	public RoleService roleService; 
	
	
	//Get All Roles
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value= ROLE_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Role> getAllRoles() {
		log.info("getAllRoles()");
		return roleService.getAllRoles();
	}
	
	
	//Get Role by Role ID
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = ROLE_END_POINT + "/{roleId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE )
	public Mono<Role> getRoleByRoleId(@PathVariable Long roleId) {
		log.info("getRoleByRoleId()");
		return roleService.getRoleByRoleId(roleId);
	}
	
	
	//Get Role by Role Name
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = ROLE_END_POINT + "/{roleName}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE )
	public Mono<Role> getRoleByRoleName(@PathVariable String roleName) {
		log.info("getRoleByRoleName()");
		return roleService.getRoleByRoleName(roleName);
	}
	
	
	//Add New Role
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = ROLE_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE ) 
	public Mono<ResponseEntity<Object>> addRole(@Valid @RequestBody RoleDto roleDto) {
		log.info("addRole()" + roleDto);
		ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
		
		return roleService.saveRole(roleDto)
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
	
	
	//Update Role
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = ROLE_END_POINT + "/{roleId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<Role>> updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDto) {
 
		log.info("updateRole()" + roleId);

		return roleService.updateRole(roleId, roleDto)
				.map(updatedRole -> new ResponseEntity<>(updatedRole, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	
	//Delete Role
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = ROLE_END_POINT + "/{roleId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public void deleteRole(@PathVariable Long roleId) {
		
		log.info("deleteRole()" + roleId);
		
		roleService.deleteRole(roleId);
	}
	
	
}
