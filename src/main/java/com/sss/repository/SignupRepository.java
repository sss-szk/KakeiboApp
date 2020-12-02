package com.sss.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.sss.model.SignupForm;

@Repository
public class SignupRepository {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public int insert(SignupForm form) throws DataAccessException{
		
		String sql = "INSERT INTO m_user(user_id,password) VALUES(?,?)";
		
		//パスワード暗号化
		String password = passwordEncoder.encode(form.getPassword());
				

		int rowNumber = jdbc.update(sql,form.getUserId(),password);
		
		return rowNumber;
		
	}
	
	/**userIdの重複チェック
	 * 
	 * @param userId
	 * true:重複なし
	 * false:重複あり
	 */
	public boolean userIdCheck(String userId) {
		
		String sql = "SELECT * FROM m_user WHERE user_id = ?";
		
		List<Map<String, Object>> result = jdbc.queryForList(sql,userId);
		
		if(result.size() == 0) {
			return true;
		}else {
			return false;
		}
		
	}
}
