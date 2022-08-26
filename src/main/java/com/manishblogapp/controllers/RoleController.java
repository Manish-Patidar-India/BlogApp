package com.manishblogapp.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manishblogapp.entities.Role;
import com.manishblogapp.exceptions.PasswordRelatedException;
import com.manishblogapp.exceptions.ResourceNotFoundException;
import com.manishblogapp.payloads.RoleDto;
import com.manishblogapp.repositories.RoleRepository;

@RestController
public class RoleController {
	
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
     private ModelMapper modelmapper;
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/role/")
	public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roledto){
		
		
       Role role = modelmapper.map(roledto, Role.class);
       Optional<Role> roleop = rolerepo.findById(roledto.getId());
       
       if(!roleop.isEmpty()) {
    	   throw new PasswordRelatedException("Role alredy existsy with this role id ");
       }
		Role savedrole = rolerepo.save(role);
		return new ResponseEntity<>(modelmapper.map(savedrole, RoleDto.class), HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/role/")
	public ResponseEntity<List<Role>> getRoles(){
		
		
		List<Role> roles = rolerepo.findAll();
		return new ResponseEntity<>(roles, HttpStatus.OK);
		
		
	}
}
