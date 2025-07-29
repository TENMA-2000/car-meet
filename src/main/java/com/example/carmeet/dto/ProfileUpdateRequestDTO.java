package com.example.carmeet.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProfileUpdateRequestDTO {

	private String name;
    private String hobbies;
    private Integer carLifeYear;
    private Integer gender;
    private String introduction;
    private MultipartFile profileImage;
}
