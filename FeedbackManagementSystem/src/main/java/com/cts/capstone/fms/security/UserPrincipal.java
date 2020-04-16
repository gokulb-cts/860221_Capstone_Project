package com.cts.capstone.fms.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cts.capstone.fms.domain.Authority;
import com.cts.capstone.fms.domain.FmsUser;
import com.cts.capstone.fms.domain.Role;

import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private FmsUser user;
	
	public UserPrincipal(FmsUser user) {
		this.user = user;
		this.userId = user.getUserId();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // returns role and their authorities in List of GrantedAuthority
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		List<Authority> authorities = new ArrayList<>();
		Role userRole = this.user.getRole();
		
		if(userRole == null) return grantedAuthorities;
		
		grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		authorities.addAll(userRole.getAuthorityList());

		authorities.forEach(authority -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		});
		
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.user.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		return String.valueOf(this.user.getUserId());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		//return this.user.getActiveStatus() == 1;
		return true;
	}

}
