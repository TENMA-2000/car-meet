package com.example.carmeet.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		log.info("【Security設定開始】SecurityFilterChainの構築を開始します");
		
		httpSecurity
				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint((request, response, authException) -> {
							log.warn("【認証エラー】未認証アクセス: {}", request.getRequestURI());
							response.sendError(401);
						}))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.GET, "/api/posts/**").authenticated()
						.requestMatchers("/error").permitAll()
						.requestMatchers("/api/auth/**", "/api/auth/login", "/api/auth/signup", "/api/posts/**").permitAll()
						.requestMatchers("/api/users/me").authenticated()
						.requestMatchers("/api/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/user/**").hasRole("GENERAL")
						.anyRequest().authenticated()
				)
				
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		log.info("【Security設定完了】SecurityFilterChainの構築が完了しました");
		
		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		log.info("【Security設定】AuthenticationManagerを初期化");
		return authenticationConfiguration.getAuthenticationManager();
	}
	

	@Bean
	public CorsConfigurationSource configurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("*"));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}
