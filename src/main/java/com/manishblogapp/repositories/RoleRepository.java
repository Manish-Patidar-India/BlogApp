package com.manishblogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manishblogapp.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	
}
