package com.cts.capstone.fms.controller;

import static com.cts.capstone.fms.constants.FmsUserConstants.ROLE_NOT_FOUND;
import static com.cts.capstone.fms.constants.FmsUserConstants.USER_END_POINT;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.dto.FmsUserDto;
import com.cts.capstone.fms.dto.FmsUserRegisterDto;
import com.cts.capstone.fms.exception.RoleNotFoundException;
import com.cts.capstone.fms.service.FmsUserService;
import com.cts.capstone.fms.service.RoleService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class FmsUserRestController {

	@Autowired
	private FmsUserService fmsUserService;

	@Autowired
	private RoleService roleService;

	
	//Get All Users
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = USER_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<FmsUser> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		log.info("getAllUsers()");
		if (page > 0)
			page -= 1;
		return fmsUserService.getAllUsers(page, limit);

	}

	
	//Get User By UserId
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
	@GetMapping(value = USER_END_POINT + "/{userId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FmsUser>> getUserByUserId(@PathVariable Long userId) {

		log.info("getUserByUserId()");
		return fmsUserService.getUserByUserId(userId).map(user -> new ResponseEntity<FmsUser>(user, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<FmsUser>(HttpStatus.NOT_FOUND));

	}

	
	//Get User By MailId
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = USER_END_POINT + "/by", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FmsUser>> getUserByEmailId(@RequestParam("email") String emailId) {

		log.info("getUserByEmailId()");
		return fmsUserService.getUserByEmailId(emailId).map(user -> new ResponseEntity<FmsUser>(user, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<FmsUser>(HttpStatus.NOT_FOUND));

	}

	
	//Get List of Users By Role
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = USER_END_POINT + "/role/{roleName}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<ResponseEntity<FmsUser>> getUsersByRole(@PathVariable String roleName) {

		log.info("getUsersByRole()");

		return roleService.getRoleByRoleName(roleName)
				.switchIfEmpty(Mono.defer(() -> Mono.error(new RoleNotFoundException(ROLE_NOT_FOUND))))
				.flatMapMany(role -> fmsUserService.getUsersByRole(role))
				.map(user -> new ResponseEntity<FmsUser>(user, HttpStatus.OK));
		/*
		 * return roleService.getRoleByRoleName(roleName) .flatMap(role ->
		 * fmsUserService.getUsersByRole(role));
		 */
		/*
		 * Mono<Role> roleMono = roleService.getRoleByRoleName(roleName); Flux<FmsUser>
		 * usersByRole = roleMono.flatMapMany(role -> {
		 * fmsUserService.getUsersByRole(role); });
		 */
		/*
		 * fmsUserService.getUsersByRole(role) .map(user -> new
		 * ResponseEntity<FmsUser>(user, HttpStatus.OK));
		 */
		// });
		// .defaultIfEmpty(Flux.just(new
		// ResponseEntity<FmsUser>(HttpStatus.NOT_FOUND)));

	}

	
	//Add New User (Register User)
	@PreAuthorize("permitAll()")
	@PostMapping(value = USER_END_POINT, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<Object>> addUser(@Valid @RequestBody FmsUserRegisterDto userDto) {

		log.info("registerUser()" + userDto);
		ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

		return fmsUserService.saveUser(userDto).map(savedUser -> {
			URI location = uriBuilder.path("/{userId}").buildAndExpand(savedUser.getUserId()).toUri();
			return ResponseEntity.created(location).build();
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	
	//Update User Details
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
	@PatchMapping(value = USER_END_POINT + "/{userId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FmsUser>> updateUser(@PathVariable Long userId, @RequestBody FmsUserDto userDto) {

		log.info("updateUser()");

		return fmsUserService.updateUser(userId, userDto)
				.map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	
	//Assign Role to User
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = USER_END_POINT
			+ "/{userId}/role/{roleName}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FmsUser>> assignUserRole(@PathVariable Long userId, @PathVariable String roleName) {

		log.info("assignUserRole()");

		return fmsUserService.updateUserRole(userId, roleName)
				.map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}
	
	
	//Remove Role of the User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = USER_END_POINT
			+ "/{userId}/role", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<ResponseEntity<FmsUser>> removeUserRole(@PathVariable Long userId) {

		log.info("removeUserRole()");

		return fmsUserService.removeUserRole(userId)
				.map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}


	//Delete User	
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
	@DeleteMapping(value = USER_END_POINT + "/{userId}")
	public void deleteUser(@PathVariable Long userId) {

		log.info("deleteUser()");
		fmsUserService.deleteUser(userId);

	}
	
}
