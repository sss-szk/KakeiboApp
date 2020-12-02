package com.sss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sss.model.DailyData;
import com.sss.repository.DailyRepository;



@Service
public class DailyService {
	
	@Autowired
	private DailyRepository repository;
	
	public List<DailyData> getDailyData(String userId,Date date){
		
		List<DailyData> list = new ArrayList<>();
		list = repository.getDailyData(userId,date);
		
		return list;
	}
	
	public List<DailyData> addCommaToPrice(List<DailyData> list){
		
		List<DailyData> newList = new ArrayList<>();
		for(DailyData data:list) {
			
			int price = data.getPrice();
			String strPrice = "";
			strPrice = "¥" + String.format("%,d", price);
			data.setStringPrice(strPrice);
			newList.add(data);
			
		}
		
		return newList;
	}
	
	public String culculateTotal(List<DailyData> list){
		
		int total = 0;
		for(DailyData data:list) {
			
			int price = data.getPrice();
			total += price;
		}
		
		return "¥" + String.format("%,d", total);
	}
	
	public void deleteOne(int id) {
		
		repository.deleteOne(id);
	
	}

}
