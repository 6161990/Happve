package com.kh.happve.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kh.happve.entity.Member;

@Transactional(readOnly=true)
public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	boolean existsByEmail(String email); //존재하는 이메일인지 확인

	boolean existsByNickname(String nickname); //존재하는 닉네임인지 확인
	
	Member findByEmail(String email); 
	
	Member findByNickname(String nickname);

}
