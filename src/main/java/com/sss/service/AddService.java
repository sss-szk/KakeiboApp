package com.sss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sss.model.AddForm;
import com.sss.repository.AddRepository;

@Service
public class AddService {
	
	@Autowired
	AddRepository repository;
	
	public void addOne(AddForm form) {
		
		repository.addOne(form);
	}

}
