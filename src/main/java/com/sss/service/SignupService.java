package com.sss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sss.model.SignupForm;
import com.sss.repository.SignupRepository;

@Service
public class SignupService {

	@Autowired
	SignupRepository repository;

	public boolean insert(SignupForm form) {

		int result = repository.insert(form);

		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean userIdCheck(String userId) {
		// userIdの重複チェック
		boolean userIdCheck = repository.userIdCheck(userId);

		if (userIdCheck == false) {
			return false;
		}else {
			return true;
		}
	}
}
