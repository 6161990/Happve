package com.kh.happve.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.happve.dto.ProfileForm;
import com.kh.happve.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileFormValidator implements Validator{
	
	private final MemberRepository memberRepository;

	@Override
	public boolean supports(Class<?> Class) {
		return Class.isAssignableFrom(ProfileForm.class);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ProfileForm profileForm=(ProfileForm)object;
		if(memberRepository.existsByEmail(profileForm.getEmail())) {
			errors.rejectValue("email", "invalid.email", new Object[] {profileForm.getEmail()}, "이미 사용중인 이메일 입니다.");
		}
		
		if(memberRepository.existsByNickname(profileForm.getNickname())) {
			errors.rejectValue("nickname", "invalid.nickname", new Object[] {profileForm.getNickname()}, "이미 사용중인 닉네임 입니다.");
			
		}
	}

}