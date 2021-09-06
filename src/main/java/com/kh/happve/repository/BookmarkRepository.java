package com.kh.happve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kh.happve.entity.Bookmark;
import com.kh.happve.entity.Member;

@Transactional(readOnly = true)
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	
	Bookmark findByMemberIdAndRestaurantId(Long memberId, Long restaurantId);

	void deleteByMemberIdAndRestaurantId(Long memberId, Long restaurantId);

	List<Bookmark> findByMemberId(Long memberId);

	List<Bookmark> findByRestaurantId(Long restaurantId);

}
