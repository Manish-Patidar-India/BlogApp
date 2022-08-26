package com.manishblogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manishblogapp.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{


	
	
	
}
