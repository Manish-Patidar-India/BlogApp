package com.manishblogapp.exceptions;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.manishblogapp.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandling {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<String> duplicateMailExceptionHandler(DuplicateEmailException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException ex) {

		Map<String, String> map = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(e -> {
			String fieldName = ((FieldError) e).getField();
			String message = e.getDefaultMessage();
			map.put(fieldName, message);

		});

		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiResponse> handleIOException(IOException ex) {

		return new ResponseEntity<>(
				new ApiResponse("Something wrong happened in uploading or downloading file", "Failure"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<String> handleDateTimeException(DateTimeParseException ex) {

		return new ResponseEntity<>("please enter correct date format(i.e.; 2007-12-03)", HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(PasswordRelatedException.class)
	public ResponseEntity<String> passworRelatedExceptionhandler(PasswordRelatedException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> accessrDeniedExceptionhandler(AccessDeniedException ex) {

		return new ResponseEntity<>("your Role  dont have access to this API", HttpStatus.BAD_REQUEST);
	}

}
