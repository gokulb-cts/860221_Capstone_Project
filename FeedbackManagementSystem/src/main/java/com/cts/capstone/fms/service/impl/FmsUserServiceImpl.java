package com.cts.capstone.fms.service.impl;

import static com.cts.capstone.fms.constants.FmsUserConstants.ROLE_NOT_FOUND;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.dto.FmsUserDto;
import com.cts.capstone.fms.dto.FmsUserRegisterDto;
import com.cts.capstone.fms.exception.RoleNotFoundException;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.repositories.RoleRepository;
import com.cts.capstone.fms.security.UserPrincipal;
import com.cts.capstone.fms.service.FmsUserService;
import com.cts.capstone.fms.service.RoleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FmsUserServiceImpl implements FmsUserService {

	@Autowired
	FmsUserRepository fmsUserRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Flux<FmsUser> getAllUsers(int page, int limit) {
		Pageable pageableRequest = PageRequest.of(page, limit);
		return Flux.fromIterable(fmsUserRepository.findAll(pageableRequest).getContent());
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
	public Mono<FmsUser> saveUser(FmsUserRegisterDto userDto) {
		
		FmsUser user = fmsUserRepository.findByUserId(userDto.getUserId());
		if (user != null && user.getEncryptedPassword() != null) {
			throw new RuntimeException("User already exists with user ID: " + userDto.getUserId());
		}
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull())
									  .setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.map(userDto, user);
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		return Mono.just(fmsUserRepository.save(user));

	}

	@Override
	public Mono<FmsUser> updateUser(Long userId, FmsUserDto userDto) {
		FmsUser user = fmsUserRepository.findByUserId(userId);
		if (user != null) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
										  .setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(userDto, user);
			if (userDto.getPassword() != null)
				user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			return Mono.just(fmsUserRepository.save(user));
		}

		return Mono.empty();

	}

	@Override
	public void deleteUser(Long userId) {

		FmsUser user = fmsUserRepository.findByUserId(userId);

		if (user == null) {
			throw new RuntimeException("User does not exists with user ID: " + userId);
		}

		fmsUserRepository.delete(user);

	}

	@Override
	public Mono<FmsUser> updateUserRole(Long userId, String roleName) {
		return roleService.getRoleByRoleName(roleName)
				.switchIfEmpty(Mono.defer(() -> Mono.error(new RoleNotFoundException(ROLE_NOT_FOUND))))
				.flatMap(role -> {
					return this.getUserByUserId(userId).flatMap(user -> {
						user.setRole(role);
						return Mono.just(fmsUserRepository.save(user));
					});
				});
	}
	

	@Override
	public Mono<FmsUser> removeUserRole(Long userId) {
		FmsUser user = fmsUserRepository.findByUserId(userId);
		if(user != null) {
			user.setRole(null);
			return Mono.just(fmsUserRepository.save(user));
		}
		
		return Mono.empty();
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		FmsUser fmsUser = fmsUserRepository.findByUserId(Long.parseLong(userId));

		if (fmsUser == null)
			throw new UsernameNotFoundException(userId);

		//return new User(String.valueOf(fmsUser.getUserId()), fmsUser.getEncryptedPassword(), new ArrayList<>());
		//Adding Authority checks 
		
		return new UserPrincipal(fmsUser); //UserPrincipal - custom class that implements Spring's UserDetails class
		
	}

}
