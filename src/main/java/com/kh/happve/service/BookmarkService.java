package com.kh.happve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.kh.happve.entity.Bookmark;
import com.kh.happve.entity.Member;
import com.kh.happve.entity.RestaurantDbEntity;
import com.kh.happve.repository.BookmarkRepository;
import com.kh.happve.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final RestaurantRepository restaurantRepository;

	// 북마크 추가
	public Bookmark create(Bookmark bookmark) {
		return bookmarkRepository.save(bookmark);
	}

	// 북마크 삭제
	public void delete(Long memberId, Long restaurantId) {
		bookmarkRepository.deleteByMemberIdAndRestaurantId(memberId, restaurantId);
	}
	
	public Bookmark findByMemAndResId(Long memberId, Long restaurantId) {
		return bookmarkRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);
	}

	public List<Bookmark> BookmarkResId(Long memberId) {
		return bookmarkRepository.findByMemberId(memberId);
	}

	public List<RestaurantDbEntity> BookmarkList() {
		return restaurantRepository.findAll();
	}

}
