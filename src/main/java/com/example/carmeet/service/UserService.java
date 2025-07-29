package com.example.carmeet.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.carmeet.dto.ProfileUpdateRequestDTO;
import com.example.carmeet.dto.SignupRequestDTO;
import com.example.carmeet.entity.Role;
import com.example.carmeet.entity.User;
import com.example.carmeet.repository.RoleRepository;
import com.example.carmeet.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public User signupUser(SignupRequestDTO signupRequestDTO) {
		if (userRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) {
			throw new IllegalStateException();
		}
		
		Role userRole = roleRepository.findByName("ROLE_GENERAL")
				.orElseThrow(IllegalStateException::new);
		
		String rawPassword = signupRequestDTO.getPassword();
		String hashedPassword = passwordEncoder.encode(rawPassword);
		log.info("登録時の平文パスワード: '{}'", rawPassword);
		log.info("登録時のハッシュ: {}", hashedPassword);
		
		User user = new User(); 
		user.setName(signupRequestDTO.getName());
		user.setEmail(signupRequestDTO.getEmail());
		user.setPassword(hashedPassword);
		user.setRole(userRole);
		user.setEnabled(true);
		
		return userRepository.save(user);
	}
	
	@Transactional
	public User updateUserProfile(Long userId, ProfileUpdateRequestDTO profileUpdateRequestDTO) {
		User user = userRepository.findById(userId)
				.orElseThrow(IllegalStateException::new);
		
		if (profileUpdateRequestDTO.getName() != null && !profileUpdateRequestDTO.getName().isBlank()) {
			user.setName(profileUpdateRequestDTO.getName());
		}
		
		if (profileUpdateRequestDTO.getCarLifeYear() != null) {
			user.setCarLifeYear(profileUpdateRequestDTO.getCarLifeYear());
		}
		
		if (profileUpdateRequestDTO.getGender() != null) {
			user.setGender(profileUpdateRequestDTO.getGender());
		}
		
		if (profileUpdateRequestDTO.getHobbies() != null) {
			user.setHobbies(profileUpdateRequestDTO.getHobbies());
		}
		
		if (profileUpdateRequestDTO.getIntroduction() != null) {
			user.setIntroduction(profileUpdateRequestDTO.getIntroduction());
		}
		
		if (profileUpdateRequestDTO.getProfileImage() != null && !profileUpdateRequestDTO.getProfileImage().isEmpty()) {
			String filename = saveProfileImage(profileUpdateRequestDTO.getProfileImage());
			user.setProfileImage(filename);
		}
		
		return userRepository.save(user);
	}
	
	private String saveProfileImage(MultipartFile multipartFile) {
		try {
			String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
			Path path = Paths.get("src/main/resources/static/images", fileName);
		
			Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			return "/images/" + fileName;
		} catch (IOException e) {
			throw new  RuntimeException(e);
		}
	}
}
