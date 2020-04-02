package com.cts.capstone.fms.service;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FmsUserService {
	
	public Flux<FmsUser> getAllUsers();
	
	public Mono<FmsUser> getUserByUserId(Long Id);
	
	public Mono<FmsUser> getUserByEmailId(String emailId);
	
	public Flux<FmsUser> getUsersByRole(Role role);
	
	public Mono<FmsUser> saveUser(FmsUser user);
}
