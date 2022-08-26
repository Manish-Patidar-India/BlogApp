package com.manishblogapp.services;

import java.util.List;

import com.manishblogapp.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userdto,Integer rolid);

	List<UserDto> getAllUsers();

	UserDto getUserById(Integer id);

	UserDto updateUser(UserDto userdto, Integer id ,Integer roleid);

	void deleteUser(Integer id);

}
