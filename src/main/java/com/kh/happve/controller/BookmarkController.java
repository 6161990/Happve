package com.kh.happve.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kh.happve.entity.Bookmark;
import com.kh.happve.entity.Member;
import com.kh.happve.service.BookmarkService;
import com.kh.happve.validator.CurrentAccount;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

	private final BookmarkService bookmarkService;

	// 북마크 추가 & 삭제
	@PostMapping("/bookmark/{memberId}/{restaurantId}")
	public String addBookmark(@RequestBody Bookmark bookmark, @PathVariable Long memberId, @PathVariable Long restaurantId) throws Exception {
		Bookmark isbookmark = bookmarkService.findByMemAndResId(memberId, restaurantId);
		if (isbookmark != null) {
			bookmarkService.delete(memberId, restaurantId);
			return "Delete";
		} else {
			bookmarkService.create(bookmark);
			return "Create";
		}
	}

	//마이페이지 즐겨찾기 확인
	@GetMapping("/mypage/bookmark/{memberId}")
	public ModelAndView bookmarkPage(@CurrentAccount Member member, @PathVariable Long memberId, Model model) {
		model.addAttribute(member);
		model.addAttribute("bookmarkmemberlist", bookmarkService.BookmarkResId(memberId));
		model.addAttribute("bookmarklist",bookmarkService.BookmarkList());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("mypagebookmark");
		return mv;
	}

}