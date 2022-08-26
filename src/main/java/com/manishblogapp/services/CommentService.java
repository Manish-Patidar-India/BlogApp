package com.manishblogapp.services;

import java.util.List;

import com.manishblogapp.payloads.CommentDto;

public interface CommentService {

	
	
	
 CommentDto createComment(Integer postid, CommentDto commentdto);
	
	
	 CommentDto getComment(Integer commentid);
	
	
	List<CommentDto> getAllComments();
	
	CommentDto editComment(Integer commentid,CommentDto comdto);
	
	
}
