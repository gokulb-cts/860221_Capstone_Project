package com.cts.capstone.fms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.service.FmsUserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FmsUserServiceImpl implements FmsUserService{
	
	@Autowired
	FmsUserRepository fmsUserRepository;
	
	@Override
	public Flux<FmsUser> getAllUsers() {
		return Flux.fromIterable(fmsUserRepository.findAll());
	}

	@Override
	public Mono<FmsUser> getUserByUserId(Long userId) {
		return Mono.justOrEmpty(fmsUserRepository.findByUserId(userId));
	}

	@Override
	public Mono<FmsUser> getUserByEmailId(String emailId) {
		return Mono.justOrEmpty(fmsUserRepository.findByEmailId(emailId));
	}

	@Override
	public Flux<FmsUser> getUsersByRole(Role role) {
		return Flux.fromIterable(fmsUserRepository.findByRole(role));
	}

	@Override
	public Mono<FmsUser> saveUser(FmsUser user) {
		return Mono.just(fmsUserRepository.save(user));
	}

}
