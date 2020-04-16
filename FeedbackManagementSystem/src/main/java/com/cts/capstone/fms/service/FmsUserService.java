package com.cts.capstone.fms.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.dto.FmsUserDto;
import com.cts.capstone.fms.dto.FmsUserRegisterDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FmsUserService extends UserDetailsService {

	public Flux<FmsUser> getAllUsers(int page, int limit);

	public Mono<FmsUser> getUserByUserId(Long Id);

	public Mono<FmsUser> getUserByEmailId(String emailId);

	public Flux<FmsUser> getUsersByRole(Role role);

	public Mono<FmsUser> saveUser(FmsUserRegisterDto userDto);

	public void deleteUser(Long userId);

	public Mono<FmsUser> updateUserRole(Long userId, String roleName);

	public Mono<FmsUser> updateUser(Long userId, FmsUserDto userDto);

	public Mono<FmsUser> removeUserRole(Long userId);

}
