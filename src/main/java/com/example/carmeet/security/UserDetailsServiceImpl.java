package com.example.carmeet.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.carmeet.entity.User;
import com.example.carmeet.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		log.debug("【認証処理開始】入力されたメールアドレス: {}", email);
		
        
        Optional<User> user = userRepository.findByEmail(email);
        
        if (user.isEmpty()) {
			log.warn("【認証失敗】メールアドレス '{}' に一致するユーザーが存在しません。", email);
			throw new UsernameNotFoundException("");
		}
        
        log.debug("【認証成功】ユーザー '{}' をロードしました。", user.get().getEmail());
        
        return new UserDetailsImpl(user.get());
	}
}
