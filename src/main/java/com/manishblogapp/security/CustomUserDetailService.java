package com.manishblogapp.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manishblogapp.entities.UserEntity;
import com.manishblogapp.exceptions.ResourceNotFoundException;
import com.manishblogapp.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userrepo.findByEmail(username);

		if (user == null) {

			throw new ResourceNotFoundException("Username", "not found with email " + username, 0);
		}

		else {
			return user;

		}

	}

}
