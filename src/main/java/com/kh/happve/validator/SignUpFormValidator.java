package com.kh.happve.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kh.happve.dto.SignUpForm;
import com.kh.happve.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator{
	
	private final MemberRepository memberRepository;

	@Override
	public boolean supports(Class<?> Class) { //해당 Validator를 지원하느냐
		return Class.isAssignableFrom(SignUpForm.class);
	}

	@Override
	public void validate(Object object, Errors errors) {
		SignUpForm signUpForm=(SignUpForm)object;

		if(memberRepository.existsByEmail(signUpForm.getEmail())) {
			errors.rejectValue("email", "invalid.email", new Object[] {signUpForm.getEmail()}, "이미 사용중인 이메일 입니다.");
		}
		
		if(memberRepository.existsByNickname(signUpForm.getNickname())) {
			errors.rejectValue("nickname", "invalid.nickname", new Object[] {signUpForm.getNickname()}, "이미 사용중인 닉네임 입니다.");
			
		}
		if(!signUpForm.getPassword().equals(signUpForm.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "invalid.password", new Object[] {signUpForm.getPasswordConfirm()}, "비밀번호가 일치하지 않습니다.");
		}
	}
}