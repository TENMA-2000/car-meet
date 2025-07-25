package com.example.carmeet.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDTO {
	
	private MultipartFile multipartFile;

	@NotBlank(message = "キャプションは必須です")
    @Size(max = 255, message = "キャプションは255文字以内で入力してください")
	private String caption;
	
	@NotBlank(message = "メディアURLは必須です")
	private String mediaUrl;
	
	@NotBlank(message = "メディアタイプは必須です")
	private String mediaType;
	
	@NotNull(message = "ストーリーかどうかを選択してください")
	private Boolean isStory;
	
	@Size(max = 100, message = "位置情報の名前は100文字以内で入力してください")
	private String locationName;
	private Double latitude;
	private Double longitude;
}
