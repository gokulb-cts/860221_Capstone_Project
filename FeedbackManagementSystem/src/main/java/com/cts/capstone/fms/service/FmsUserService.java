package com.cts.capstone.fms.service;

import com.cts.capstone.fms.DTO.FmsUser;
import com.cts.capstone.fms.DTO.Role;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FmsUserService {
	
	public Mono<FmsUser> getUserByUserId(Long Id);
	
	public Mono<FmsUser> getUserByEmailId(String emailId);
	
	public Flux<FmsUser> getUsersByRole(Role role);
}
