package com.manishblogapp.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manishblogapp.entities.Category;
import com.manishblogapp.entities.Posts;
import com.manishblogapp.entities.UserEntity;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {

	
	Page<Posts> findByUser(UserEntity user,Pageable p);
	
	List<Posts> findByCategory(Category category);
	
	Integer deleteByUser(UserEntity user);
	
	List<Posts> findByContentContainingIgnoreCase(String searchwith);
	
	
	List<Posts> findByDate(LocalDate date);
	
}
