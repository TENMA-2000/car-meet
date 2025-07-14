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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService authService;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	public AuthController(UserService authService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
		this.authService = authService;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponseDTO> signupUser(@RequestBody SignupRequestDTO signupRequestDTO) {
		try {
			User signupUser = authService.signupUser(signupRequestDTO);
			AuthResponseDTO authResponseDTO = new AuthResponseDTO("ユーザー登録が完了しました。", null, signupUser.getUserId(),
					signupUser.getName());
			return new ResponseEntity<>(authResponseDTO, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			AuthResponseDTO errorResponseDTO = new AuthResponseDTO(e.getMessage(), null, null, null);
			return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequestDTO.getEmail(),
							loginRequestDTO.getPassword()));

			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

			String token = jwtUtil.generateToken(userDetailsImpl);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			AuthResponseDTO authResponseDTO = new AuthResponseDTO(
					"ログインに成功しました。",
					token,
					userDetailsImpl.getUser().getUserId(),
					userDetailsImpl.getUser().getName());

			return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
		} catch (RuntimeException e) {
			AuthResponseDTO erroAuthResponseDTO = new AuthResponseDTO(e.getMessage(), null, null, null);
			return new ResponseEntity<>(erroAuthResponseDTO, HttpStatus.UNAUTHORIZED);
		}
	}
}
