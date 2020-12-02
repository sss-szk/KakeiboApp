package com.sss.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sss.model.AddForm;
import com.sss.service.AddService;

@Controller
public class AddController {
	
	@Autowired
	AddService service;
	
	@GetMapping("/{userId:.+}/daily/{clickedDate:.+}/add")
	public String getAdd(@ModelAttribute AddForm form ,Model model,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date clickedDate,
			@PathVariable("clickedDate") String strDate,
			@PathVariable("userId") String userId) {
		
		form.setDay(clickedDate);
		form.setUserId(userId);
		
		model.addAttribute("addForm",form);
		model.addAttribute("strDate",strDate);
		model.addAttribute("contents", "add :: add_contents");
		return "layout";
	}
	
	@PostMapping("/{userId:.+}/daily/{clickedDate:.+}/add")
	public String postSignUp(@ModelAttribute @Validated AddForm form,BindingResult bindingResult,
			Model model,			
			@PathVariable("clickedDate") String strDate,
			@PathVariable("userId") String userId){
		
		//入力チェックに引っかかった場合、ユーザ登録画面に戻る
		if(bindingResult.hasErrors()) {
			return getAdd(form, model,form.getDay(),strDate,userId);
		}
	
		//formの中身をコンソールに出して確認
		//System.out.println(form);
		
		//受け取ったデータをDBに登録
		service.addOne(form);
		
		//一つ前の画面にリダイレクト
		return "redirect:/" + userId + "/daily/" + strDate;
	}

}
