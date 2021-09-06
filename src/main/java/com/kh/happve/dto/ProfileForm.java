package com.kh.happve.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ProfileForm {
	
	@NotBlank
	@Length(min=3,max=20)
	@Pattern(regexp="^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
	private String nickname;
	
	@Email
	@NotBlank
	private String email;
	
	private String vtype; //채식 단계
	
}
