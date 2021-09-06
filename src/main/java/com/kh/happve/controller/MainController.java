package com.kh.happve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.happve.entity.Member;
import com.kh.happve.validator.CurrentAccount;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String home(@CurrentAccount Member member,Model model) {
		if(member != null) {
			model.addAttribute(member);
		}
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/mypage")
	public String mypage(@CurrentAccount Member member,Model model) {
		model.addAttribute(member);
		return "mypage";
	}

}
