package com.sss.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class SignupForm {

	@NotBlank
	private String userId;
	
	@NotBlank
	@Length(min=8,max=50)
	private String password;
}
