package com.example.carmeet.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.carmeet.entity.User;
import com.example.carmeet.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> {
					System.out.println("DEBUG: UserDetailsService - User not found for email: " + email);
				return new UsernameNotFoundException("ユーザーが見つかりませんでした: " + email);
				});
		
		System.out.println("DEBUG: UserDetailsService - User found: " + user.getEmail());
        System.out.println("DEBUG: UserDetailsService - Stored hashed password: " + user.getPassword());
        System.out.println("DEBUG: UserDetailsService - User enabled status: " + user.getEnabled());
        System.out.println("DEBUG: UserDetailsService - User role: " + user.getRole().getName());
        
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
        System.out.println("DEBUG: UserDetailsService - UserDetailsImpl created. Password from UserDetailsImpl: " + userDetailsImpl.getPassword());
		return new UserDetailsImpl(user);
	}
}
