package com.sss.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sss.model.DailyData;

@Repository
public class DailyRepository {

	@Autowired
	private JdbcTemplate jdbc;

	// 1日分のデータを取得するSQL
	private static final String SELECT_DAILY_DATA_SQL = "SELECT id,category,price,comment FROM m_kakei_data WHERE user_id = ? AND day = ?";

	//1件削除するSQL
	private static final String DELETE_ONE_SQL = "DELETE FROM m_kakei_data WHERE id = ?";
	
	public List<DailyData> getDailyData(String userId, Date date) {

		// select実行
		List<Map<String, Object>> dailyList = jdbc.queryForList(SELECT_DAILY_DATA_SQL, userId,date);

		// 結果返却用のList作成
		List<DailyData> list = new ArrayList<>();

		for (Map<String, Object> map : dailyList) {

			// データを取得
			int id = (int)map.get("id");
			String category = (String) map.get("category");
			int price = (int)map.get("price");
			String comment = (String)map.get("comment");

			// SimpleGrantedAuthorityインスタンスの生成
			DailyData dailyData = new DailyData();
			dailyData.setId(id);
			dailyData.setCategory(category);
			dailyData.setPrice(price);
			dailyData.setComment(comment);

			// 結果返却用のListにインスタンスを追加
			list.add(dailyData);
		}

		return list;
	}
	
	public void deleteOne(int id) throws DataAccessException {
		
		jdbc.update(DELETE_ONE_SQL, id);
	}

}
