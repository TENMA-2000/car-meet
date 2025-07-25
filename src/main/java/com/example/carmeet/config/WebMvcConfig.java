package com.example.carmeet.config;

import java.nio.file.Paths;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
		String uploadPath = Paths.get("upload-dir").toAbsolutePath().toUri().toString();

		resourceHandlerRegistry.addResourceHandler("/uploads/**")
				.addResourceLocations(uploadPath);
	}
}
