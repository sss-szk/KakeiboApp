package com.sss.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sss.model.AddForm;

@Repository
public class AddRepository {

	@Autowired
	private JdbcTemplate jdbc;

	private static final String INSERT_SQL = "INSERT INTO m_kakei_data(category,price,comment,day,user_id) VALUES(?,?,?,?,?)";

	public void addOne(AddForm form) throws DataAccessException {

		jdbc.update(INSERT_SQL,form.getCategory(),form.getPrice(),form.getComment(),form.getDay(),form.getUserId());

	}
}
