package com.sss.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sss.model.DailyData;
import com.sss.service.DailyService;

@Controller
public class DailyController {
	
	@Autowired
	DailyService service;
	
	@GetMapping("/{userId:.+}/daily/{clickedDate:.+}")
	public String getDaily(@PathVariable("clickedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date clickedDate,
			@PathVariable("clickedDate") String strDate,
			@PathVariable("userId") String userId, Model model) {
		
		//DBから選ばれた日付のデータを取得する
		List<DailyData> list = new ArrayList<>();
		list = service.getDailyData(userId, clickedDate);
		//priceをカンマ区切りに変換
		list = service.addCommaToPrice(list);
		//合計金額を計算
		String total = "0";
		total = service.culculateTotal(list);
		
		model.addAttribute("dailyList",list);
		model.addAttribute("total",total);
		model.addAttribute("strDate",strDate);
		model.addAttribute("clickedDate",clickedDate);
		model.addAttribute("contents", "daily :: daily_contents");
		return "layout";
	}
	
	@GetMapping("/{userId:.+}/daily/{clickedDate:.+}/delete/{id:.+}")
	public String getDailyDelete(@PathVariable("id") int id,
								@PathVariable("clickedDate") String strDate,
								@PathVariable("userId") String userId) {
		
		//System.out.println(id);
		
		//削除する
		service.deleteOne(id);
		
		return "redirect:/" + userId + "/daily/" + strDate;
	}

}
