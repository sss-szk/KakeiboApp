package com.sss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sss.model.SignupForm;
import com.sss.service.SignupService;

@Controller
public class SignupController {

		@Autowired
		private SignupService service;
		
		@GetMapping("/signup")
		public String getSignUp(@ModelAttribute SignupForm form,Model model) {
			
			model.addAttribute("contents", "signup :: signup_contents");
			return "/layout";
		}
		
		@PostMapping("/signup")
		public String postSignUp(@ModelAttribute @Validated SignupForm form,
									BindingResult bindingResult,
									Model model){
			//入力チェックに引っかかった場合、ユーザ登録画面に戻る
			if(bindingResult.hasErrors()) {
				return getSignUp(form, model);
			}
			
			//formの中身をコンソールに出して確認
			System.out.println(form);
			
			SignupForm user = new SignupForm();
			user.setUserId(form.getUserId());
			user.setPassword(form.getPassword());
			
			//ユーザーIDの重複チェック
			boolean userIdCheck = service.userIdCheck(user.getUserId());
			if(userIdCheck == false) {
				model.addAttribute("userIdError","ユーザーIDは既に使用されています");
				return getSignUp(form,model);
			}
			
			
			//ユーザー登録実行
			boolean result = service.insert(user);
			
			if(result == true) {
				System.out.println("insert成功");
			}else {
				System.out.println("insert失敗");
			}
			
			//login.htmlにリダイレクト
			return "redirect:/login";
		}
		
}
