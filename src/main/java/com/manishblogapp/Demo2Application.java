package com.manishblogapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.manishblogapp.entities.Role;
import com.manishblogapp.repositories.RoleRepository;

@SpringBootApplication
public class Demo2Application implements CommandLineRunner{

	@Autowired
	RoleRepository rolerepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Demo2Application.class, args);
	}
	
   
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}



	@Override
	public void run(String... args) throws Exception {
		Role role=new Role();
		role.setId(101);
		role.setRole("admin");
		rolerepo.save(role);
	}

	
	
	
	
}
