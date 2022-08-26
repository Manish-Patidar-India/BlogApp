package com.manishblogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manishblogapp.payloads.ApiResponse;
import com.manishblogapp.payloads.UserDto;
import com.manishblogapp.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register/{roleid}/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto,@PathVariable Integer roleid) {

		UserDto user = userService.createUser(userdto,roleid);

		return new ResponseEntity<>(user, HttpStatus.CREATED);

	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
        
		log.info("calling userservice methodfrom controlller");
		List<UserDto> allUsers = userService.getAllUsers();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {

		UserDto userById = userService.getUserById(id);
		return new ResponseEntity<>(userById, HttpStatus.OK);

	}
    
	@PreAuthorize("hasAuthority('admin')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {

		userService.deleteUser(id);
		return new ResponseEntity<>(new ApiResponse("User deleted ", "Success!!"), HttpStatus.OK);

	}

	@PutMapping("/{roleid}/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto, @PathVariable Integer id,@PathVariable Integer roleid) {

		UserDto user = userService.updateUser(userdto, id,	roleid);

		return new ResponseEntity<>(user, HttpStatus.OK);

	}

}
