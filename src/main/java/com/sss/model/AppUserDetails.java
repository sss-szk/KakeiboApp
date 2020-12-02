package com.sss.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDetails implements UserDetails{

	private String userId;
	private String password;
	private Collection<? extends GrantedAuthority> authority;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authority;
	}
	@Override
	public String getUsername() {
		
		return this.userId;
	}
	@Override
	public String getPassword() {
		
		return this.password;
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
		
		return true;
	}
}
