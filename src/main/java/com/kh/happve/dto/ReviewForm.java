package com.kh.happve.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReviewForm {
	
		@NotBlank(message="내용을 입력해주세요")
		private String content;
		
		private int rating;

}
