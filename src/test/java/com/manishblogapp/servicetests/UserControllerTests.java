package com.manishblogapp.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.manishblogapp.controllers.UserController;
import com.manishblogapp.payloads.UserDto;
import com.manishblogapp.services.UserServiceImpl;

@SpringBootTest(classes= {UserControllerTests.class})
public class UserControllerTests {

	@Mock
	UserServiceImpl userservice;
	
	@InjectMocks
	UserController usercontroller;
	
	@Test
	public void getAllUsersTest() {
		
		List<UserDto>userdto=new ArrayList<>();
		
		UserDto userdt=new UserDto();
		userdt.setName("manish");
		
		
		userdto.add(userdt);
		
		when(userservice.getAllUsers()).thenReturn(userdto);
		
		
		ResponseEntity<List<UserDto>> allUsers = usercontroller.getAllUsers();
		
		assertEquals("manish", allUsers.getBody().get(0).getName());
		assertEquals(200, allUsers.getStatusCodeValue());
		
		
		
	}
	
	
	
	
	
}
