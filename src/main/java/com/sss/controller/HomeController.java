package com.sss.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	

	@GetMapping("/home")
	public String getHome(Model model) {

		model.addAttribute("contents", "home :: home_contents");

		return "/layout";
	}
	
	@GetMapping("/homeLogin")
	public String getHomeLogin(Model model, Principal principal) {

		//ログインユーザーのIDを取得
		String userId = principal.getName();
		model.addAttribute("userId", userId);

		model.addAttribute("contents", "homeLogin :: homeLogin_contents");

		return "/layout";
	}

}
