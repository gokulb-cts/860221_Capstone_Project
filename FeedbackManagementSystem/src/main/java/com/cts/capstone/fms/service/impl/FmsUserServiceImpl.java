package com.cts.capstone.fms.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.dto.FmsUserDto;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.service.FmsUserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FmsUserServiceImpl implements FmsUserService {

	@Autowired
	FmsUserRepository fmsUserRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
	public Mono<FmsUser> saveUser(FmsUserDto userDto) {
		
		if(fmsUserRepository.findByUserId(userDto.getUserId()) != null) {
			throw new RuntimeException("User already exists with user ID: " + userDto.getUserId());
		}
		FmsUser user = new FmsUser();
		BeanUtils.copyProperties(userDto, user);
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		return Mono.just(fmsUserRepository.save(user));
		
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		FmsUser fmsUser = fmsUserRepository.findByUserId(Long.parseLong(userId));
		
		if(fmsUser == null) throw new UsernameNotFoundException(userId);
		
		return new User(String.valueOf(fmsUser.getUserId()), fmsUser.getEncryptedPassword(), new ArrayList<>()); //User class of Spring core
		
	}

}
