package com.sss.model;

import java.util.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AddForm {
	
	@NotBlank
	private String category;
	
	private String comment;
	
	@Min(value = 1)
	private int price;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date day;
	
	private String userId;
}
