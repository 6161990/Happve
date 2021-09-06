package com.kh.happve.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="member")
public class Member {
	
		@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="member_id")
		private Integer memberId;
		
		@Column(unique=true)
		private String email;
		
		@Column(unique=true)
		private String nickname;
		
		@Column(nullable=false)
		private String password;
		
		@Column(nullable=true)
		private String vtype; //채식 단계
		
		private String role; //관리자인지 일반회원인지 확인
		
		

}
