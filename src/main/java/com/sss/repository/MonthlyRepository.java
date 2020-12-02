package com.sss.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyRepository {

	@Autowired
	JdbcTemplate jdbc;
	
	public int getDailyTotal(Date date,String userId) {
		
		String sql = "SELECT price FROM m_kakei_data WHERE day = ? AND user_id = ?";
		
		List<Map<String, Object>> dailyList = jdbc.queryForList(sql,date,userId);
		int result = 0;
		
		for (Map<String, Object> map : dailyList) {
			// データを取得
			int price = (int)map.get("price");
			// 結果返却用のListにインスタンスを追加
			result += price;
		}
		
		return result;
	}
}
