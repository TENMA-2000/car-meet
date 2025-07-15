package com.example.carmeet.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.carmeet.dto.ProfileUpdateRequestDTO;
import com.example.carmeet.dto.SignupRequestDTO;
import com.example.carmeet.entity.Role;
import com.example.carmeet.entity.User;
import com.example.carmeet.repository.RoleRepository;
import com.example.carmeet.repository.UserRepository;



@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public User signupUser(SignupRequestDTO signupRequestDTO) {
		if (userRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) {
			throw new RuntimeException("このメールアドレスは既に登録されています。"); //TODO RuntimeExceptionの意味と
		}
		
		String hashedPassword = passwordEncoder.encode(signupRequestDTO.getPassword()); //TODO hashedPasswordとはどういう意味か？
		
		Role userRole = roleRepository.findByName("USER")
				.orElseThrow(() -> new RuntimeException("デフォルトの'USER'ロールが見つかりません。データベースを確認してください。"));
		
		//TODO 以下でやっていることについて詳しく説明をしてほしい。
		User user = new User(); 
		user.setName(signupRequestDTO.getName());
		user.setEmail(signupRequestDTO.getEmail());
		user.setPassword(hashedPassword);
		user.setRole(userRole);
		user.setEnabled(true);
		
		return userRepository.save(user);
	}
	
	//@Transactional(readOnly = true)
	//public User authenticateUser(LoginRequestDTO loginRequestDTO) {
		//User user = userRepository.findByEmail(loginRequestDTO.getEmail())
				//.orElseThrow(() -> new RuntimeException("メールアドレスまたはパスワードが間違っています。"));
		
		//if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
			//throw new RuntimeException("メールアドレスまたはパスワードが間違っています。");
		//}
		
		//if (!user.getEnabled()) {
			//throw new RuntimeException("アカウントが無効です。メール認証を完了してください。");
		//}
		
		//return user;
	//}
	
	@Transactional
	public User updateUserProfile(Long userId, ProfileUpdateRequestDTO profileUpdateRequestDTO) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("ユーザーが見つかりませんでした。"));
		
		if (profileUpdateRequestDTO.getName() != null && !profileUpdateRequestDTO.getName().isBlank()) {
			user.setName(profileUpdateRequestDTO.getName());
		}
		
		return userRepository.save(user);
	}
}
