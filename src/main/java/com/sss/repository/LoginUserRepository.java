package com.sss.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.sss.model.AppUserDetails;

@Repository
public class LoginUserRepository {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	private static final String SELECT_USER_SQL = "SELECT * FROM m_user WHERE user_id = ?";
	
	//ユーザー情報を取得してUserDetailsを生成するメソッド
	public UserDetails selectone(String userId) {
		Map<String,Object> userMap = jdbc.queryForMap(SELECT_USER_SQL,userId);
		
		AppUserDetails user = buildUserDetails(userMap);
		
		return user;
	}
	
	//ユーザークラスの生成
	private AppUserDetails buildUserDetails(Map<String,Object>userMap) {
		
		List<GrantedAuthority> list = new ArrayList<>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
		list.add(authority);
		
		String userId = (String)userMap.get("user_id");
		String password = (String)userMap.get("password");
		
		AppUserDetails user = new AppUserDetails()
				.builder()
				.userId(userId)
				.password(password)
				.authority(list)
				.build();
		
		return user;
	}
}
