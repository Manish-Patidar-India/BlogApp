package com.manishblogapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manishblogapp.entities.Role;
import com.manishblogapp.entities.UserEntity;
import com.manishblogapp.exceptions.DuplicateEmailException;
import com.manishblogapp.exceptions.ResourceNotFoundException;
import com.manishblogapp.payloads.UserDto;
import com.manishblogapp.repositories.RoleRepository;
import com.manishblogapp.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository rolrepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userdto, Integer rolid) {

		Role role = rolrepo.findById(rolid).orElseThrow(() -> new ResourceNotFoundException("Role", "roleid", rolid));
		List<Role>list=new ArrayList<>();
		list.add(role);
		UserEntity user = userRepo.findByEmail(userdto.getEmail());

		if (user != null) {

			throw new DuplicateEmailException("Email already Exist !!");
		} else {

			UserEntity users = modelMapper.map(userdto, UserEntity.class);

			users.setRoles(list);

			users.setPassword(passwordEncoder.encode(userdto.getPassword()));

			UserEntity saveduser = userRepo.save(users);

			return modelMapper.map(saveduser, UserDto.class);

		}
	}

	@Override
	public List<UserDto> getAllUsers() {

		log.info("calling repo method");
		List<UserEntity> users = userRepo.findAll();
		
		List<UserDto> userdto = users.stream().map(e -> modelMapper.map(e, UserDto.class)).collect(Collectors.toList());
log.info("returning userdto from service to controller");
		return userdto;
	}

	@Override
	public UserDto getUserById(Integer id) {

		UserEntity user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "ID", id));
          log.info(user.getName());
		return modelMapper.map(user, UserDto.class);

	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer id, Integer roleid) {

		List<Role> list = new ArrayList<>();
		UserEntity user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "ID", id));
		Role role = rolrepo.findById(roleid).orElseThrow(() -> new ResourceNotFoundException("Role", "roleid", roleid));
		list.add(role);

		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setContact(userdto.getContact());
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));

		user.setRoles(list);

		UserEntity saveduser = userRepo.save(user);

		return modelMapper.map(saveduser, UserDto.class);
	}

	@Override
	public void deleteUser(Integer id) {
		userRepo.deleteById(id);
	}

}
