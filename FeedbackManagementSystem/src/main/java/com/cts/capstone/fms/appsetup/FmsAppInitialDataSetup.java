package com.cts.capstone.fms.appsetup;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cts.capstone.fms.domain.Authority;
import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;
import com.cts.capstone.fms.enums.AuthorityEnum;
import com.cts.capstone.fms.enums.RoleEnum;
import com.cts.capstone.fms.repositories.AuthorityRepository;
import com.cts.capstone.fms.repositories.FmsUserRepository;
import com.cts.capstone.fms.repositories.RoleRepository;

@Component
public class FmsAppInitialDataSetup {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private FmsUserRepository userRepository;
	
	@Autowired 
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		Authority readAuthority = this.createAuthority(AuthorityEnum.READ_AUTHORITY.toString(), AuthorityEnum.READ_AUTHORITY.getAuthority());
		Authority writeAuthority = this.createAuthority(AuthorityEnum.WRITE_AUTHORITY.toString(), AuthorityEnum.WRITE_AUTHORITY.getAuthority());
		Authority deleteAuthority = this.createAuthority(AuthorityEnum.DELETE_AUTHORITY.toString(), AuthorityEnum.DELETE_AUTHORITY.getAuthority());
		
		Role adminRole = this.createRole(RoleEnum.ROLE_ADMIN.toString(), RoleEnum.ROLE_ADMIN.getRole(), Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
		this.createRole(RoleEnum.ROLE_PMO.toString(), RoleEnum.ROLE_PMO.getRole(), Arrays.asList(readAuthority,writeAuthority));
		this.createRole(RoleEnum.ROLE_POC.toString(), RoleEnum.ROLE_POC.getRole(), Arrays.asList(readAuthority,writeAuthority));
		this.createRole(RoleEnum.ROLE_PARTICIPANT.toString(), RoleEnum.ROLE_PARTICIPANT.getRole(), Arrays.asList(readAuthority,writeAuthority));
		
		
		//Create test admin user
		FmsUser user = userRepository.findByUserId((long) 1);
		if(user == null) {
			user = new FmsUser();
			user.setUserId((long) 1);
			user.setUserName("admin");
			user.setEncryptedPassword(bcryptPasswordEncoder.encode("admin"));
			user.setEmailId("admin@domain.com");
			user.setRole(adminRole);
			userRepository.save(user);
		}
	}

	private Authority createAuthority(String authorityName, String authorityDescription) {
	
		Authority authority = authorityRepository.findByNameIgnoreCase(authorityName);
		
		if(authority == null) {
			authority = new Authority();
			authority.setName(authorityName);
			authority.setDescription(authorityDescription);
			authorityRepository.save(authority);
		}
		
		return authority;
		
	}
	
	private Role createRole(String roleName, String roleDescription, List<Authority> authorities) {
		
		Role role = roleRepository.findByRoleNameIgnoreCase(roleName);
		
		if(role == null) {
			role = new Role();
			role.setRoleName(roleName);
			role.setRoleDescription(roleDescription);
			role.setAuthorityList(authorities);
			roleRepository.save(role);
		}
		
		return role;
		
	}
	
}
