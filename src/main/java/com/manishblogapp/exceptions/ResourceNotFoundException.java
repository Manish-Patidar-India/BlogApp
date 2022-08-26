package com.manishblogapp.exceptions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	private String resourceName;
	
	private String searchingWith;

	private long value;
	
	public ResourceNotFoundException(String resourceName, String searchingWith, long value) {
		super(resourceName+" not found with "+searchingWith+" "+value);
		this.resourceName = resourceName;
		this.searchingWith = searchingWith;
		this.value = value;
	}

	

	
	
}
