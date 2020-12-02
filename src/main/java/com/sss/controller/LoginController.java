package com.sss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		
		model.addAttribute("contents", "login :: login_contents");
	
		return "/layout";
	}

}
