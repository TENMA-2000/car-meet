package com.example.carmeet.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.dto.ProfileResponseDTO;
import com.example.carmeet.dto.ProfileUpdateRequestDTO;
import com.example.carmeet.entity.User;
import com.example.carmeet.security.UserDetailsImpl;
import com.example.carmeet.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	public ResponseEntity<ProfileResponseDTO> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

		User user = userDetailsImpl.getUser();
		ProfileResponseDTO profileResponseDTO = new ProfileResponseDTO(
				user.getUserId(), 
				user.getName(), 
				user.getEmail(), 
				user.getCarLifeYear(), 
				user.getGender(),
				user.getHobbies(), 
				user.getIntroduction(),
				user.getProfileImage());
		return ResponseEntity.ok(profileResponseDTO);
	}

	@PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProfileResponseDTO> updateMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@ModelAttribute ProfileUpdateRequestDTO profileUpdateRequestDTO) {
		User updated = userService.updateUserProfile(userDetailsImpl.getUser().getUserId(), profileUpdateRequestDTO);
		ProfileResponseDTO profileResponseDTO = new ProfileResponseDTO(updated.getUserId(), updated.getName(),
				updated.getEmail(), updated.getCarLifeYear(), updated.getGender(), updated.getHobbies(),
				updated.getIntroduction(), updated.getProfileImage());
		return ResponseEntity.ok(profileResponseDTO);
	}

}
