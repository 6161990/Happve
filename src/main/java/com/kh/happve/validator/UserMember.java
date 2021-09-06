package com.kh.happve.validator;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.kh.happve.entity.Member;

import lombok.Getter;

@Getter
//스프링 시큐리티가 다루는 유저 정보를 우리가 갖고 있는 유저 정보와 연동
public class UserMember extends User {
	
	private Member member; //우리가 가지고 있는 정보
	
	public UserMember(Member member) {
		super(member.getNickname(), member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole())));
		this.member = member;
		
	}
}

