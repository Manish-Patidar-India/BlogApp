package com.manishblogapp.payloads;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private Integer id;
	
	@NotEmpty(message="title should not be empty")
	private String title;
	
	
}
