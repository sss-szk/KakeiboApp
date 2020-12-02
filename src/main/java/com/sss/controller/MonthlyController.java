package com.sss.controller;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sss.service.MonthlyService;

@Controller
public class MonthlyController {

	@Autowired
	MonthlyService service;

	@GetMapping("*/monthly")
	public String getMonth(Model model, Principal principal) throws ParseException {

		// ログインユーザーのIDを取得
		String userId = principal.getName();
		model.addAttribute("userId", userId);

		// serviceで今月のカレンダーを格納した配列を作成しmodelに渡す
		int[][] monthArray = service.createMonthArray("");
		model.addAttribute("monthArray", monthArray);

		String[][] monthStringArray = service.createMonthStringArray("");
		model.addAttribute("monthStringArray", monthStringArray);
		
		//各セルごとの合計金額を配列に格納
		String[][] dailyTotal = service.createDailyTotal(monthStringArray,userId);
		model.addAttribute("dailyTotal",dailyTotal);
		
		//初期値設定用メソッドを実行
		String month = service.initialMonth(monthStringArray);
		model.addAttribute("month",month);

		model.addAttribute("contents", "monthly :: monthly_contents");
		return "layout";
	}

	@PostMapping("*/monthly")
	public String postMonth(@RequestParam("month")String month, Model model, Principal principal) throws ParseException {

		// ログインユーザーのIDを取得
		String userId = principal.getName();
		model.addAttribute("userId", userId);

		// serviceで今月のカレンダーを格納した配列を作成しmodelに渡す
		int[][] monthArray = service.createMonthArray(month);
		model.addAttribute("monthArray", monthArray);

		String[][] monthStringArray = service.createMonthStringArray(month);
		model.addAttribute("monthStringArray", monthStringArray);
		
		//各セルごとの合計金額を配列に格納
		String[][] dailyTotal = service.createDailyTotal(monthStringArray,userId);
		model.addAttribute("dailyTotal",dailyTotal);
		
		model.addAttribute("month",month);

		model.addAttribute("contents", "monthly :: monthly_contents");
		return "layout";
	}

}
