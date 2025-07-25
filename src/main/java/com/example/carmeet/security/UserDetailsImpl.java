package com.example.carmeet.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.carmeet.entity.User;

public class UserDetailsImpl implements UserDetails{

	private final User user;
	private final Collection<GrantedAuthority> authorities;
	
	public UserDetailsImpl(User user) {
		this.user = user;
		this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleId()));
	}
	
	public User getUser() {
		return user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
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
		return user.getEnabled();
	}
}
