package com.manishblogapp.payloads;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

	private Integer id;
	
	@Size(max=20 ,message="name should be not more than 20 characters")
	@NotEmpty(message="name should be not empty")
	private String name;
	
	@Email(message="email is invalid")
	@NotEmpty(message="email should be not empty")
	private String email;
	

	@Pattern(regexp = "^[0-9]+$", message ="invalid Contact ")
	@Size(min=10, max=10 ,message="contact  should be of length 10")
	private String contact;
	
	
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{6,9}$", message ="password should contain atleast one small Alphabet, one capital Alphabet, one digit, one special character and should be of length of between 6 to 10 character ")
	private String password;
	
	private List<RoleDto> roles=new ArrayList<>();
	
}
