package com.manishblogapp.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.manishblogapp.entities.Posts;
import com.manishblogapp.entities.Role;
import com.manishblogapp.entities.UserEntity;
import com.manishblogapp.exceptions.ResourceNotFoundException;
import com.manishblogapp.payloads.UserDto;
import com.manishblogapp.repositories.UserRepository;
import com.manishblogapp.services.UserService;
import com.manishblogapp.services.UserServiceImpl;

@SpringBootTest()
public class UserServiceImplTest {

	@Mock
	private UserRepository userrepo;

	@Spy
	private ModelMapper modelmap;

	@InjectMocks
	private UserServiceImpl userservice;

	List<UserEntity> users;
	UserEntity userentity;

	@BeforeEach
	public void setUser() {
		users = new ArrayList<>();
		userentity = new UserEntity();
		userentity.setContact("9915050563");
		userentity.setEmail("m@gmail.com");
		userentity.setId(150);
		userentity.setName("manish");
		userentity.setPassword("m123");
		userentity.setRoles(null);
		userentity.setPost(null);
		users.add(userentity);
	}

	@Test
	public void getAllUsersTest() {

		when(userrepo.findAll()).thenReturn(users);
		List<UserDto> userdto = userservice.getAllUsers();

		assertEquals(1, userdto.size());
		assertEquals("manish", userdto.get(0).getName());

	}

	@Test
	public void getUserById() {

		// UserDto userdto=new UserDto(); userdto.setName("manish");
		when(userrepo.findById(anyInt())).thenReturn(Optional.of(userentity));
		// when(modelmap.map(userentity, UserDto.class)).thenReturn(userdto);

		UserDto user = userservice.getUserById(150);

		assertNotNull(user);
		assertEquals("manish", user.getName());

	}

	@Test
	public void getUserByIdWhenIdNull() {

		Optional<UserEntity> user = Optional.empty();

		when(userrepo.findById(anyInt())).thenReturn(user);

		assertThrows(ResourceNotFoundException.class, () -> userservice.getUserById(150));

	}
	
	@Test
	public void deleteUserTest() {
		doNothing().when(userrepo).deleteById(anyInt());   //not required by default wo do nothing he rhega if method is returning void
		userservice.deleteUser(1);
		verify(userrepo, Mockito.times(1)).deleteById(1);
		
	}
	
	
	
	

	@AfterEach
	void teardown() {

		users.clear();
	}

}
