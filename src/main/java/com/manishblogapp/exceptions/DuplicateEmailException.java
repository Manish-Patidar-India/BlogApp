package com.manishblogapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateEmailException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public DuplicateEmailException(String message) {
		super(message);
		this.message = message;
	}


}
