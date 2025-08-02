package com.example.carmeet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.dto.AuthResponseDTO;
import com.example.carmeet.dto.LoginRequestDTO;
import com.example.carmeet.dto.SignupRequestDTO;
import com.example.carmeet.entity.User;
import com.example.carmeet.security.JwtUtil;
import com.example.carmeet.security.UserDetailsImpl;
import com.example.carmeet.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	public AuthController(UserService authService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
		this.userService = authService;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponseDTO> signupUser(@RequestBody SignupRequestDTO signupRequestDTO) {
		User user = userService.signupUser(signupRequestDTO);
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);

		String token = jwtUtil.generateToken(userDetailsImpl);
		return new ResponseEntity<>(new AuthResponseDTO(token, user.getUserId()), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
		log.info("Authenticating user with email: {}", loginRequestDTO.getEmail());

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
			String token = jwtUtil.generateToken(userDetailsImpl);
			
			log.info("認証成功： userId={}, Email={}", userDetailsImpl.getUser().getUserId(), userDetailsImpl.getUsername());
			log.info("生成されたトークン： {}", token);			
			
			return new ResponseEntity<>(new AuthResponseDTO(token, userDetailsImpl.getUser().getUserId()), HttpStatus.OK);
			
		} catch (Exception e) {
			log.error("認証に失敗しました。", e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
