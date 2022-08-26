package com.manishblogapp.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manishblogapp.entities.Comment;
import com.manishblogapp.entities.Posts;
import com.manishblogapp.exceptions.ResourceNotFoundException;
import com.manishblogapp.payloads.CommentDto;
import com.manishblogapp.payloads.PostsDto;
import com.manishblogapp.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository comrepo;
	
	@Autowired
	ModelMapper modelmapper;
	
	@Autowired
	PostService postservice;
	
	@Override
	public CommentDto createComment(Integer postid, CommentDto commentdto) {
		
		
	PostsDto postdto = postservice.getPost(postid);
	Comment com = modelmapper.map(commentdto, Comment.class);
	
	
		com.setCommentDate(LocalDate.now());
		com.setPost(modelmapper.map(postdto, Posts.class));
		
		Comment comment = comrepo.save(com);
	
		
		
		CommentDto cdto= modelmapper.map(comment, CommentDto.class);
		
		return cdto;
	}

	@Override
	public CommentDto getComment(Integer commentid) {
		
		
		Comment comment = comrepo.findById(commentid).orElseThrow(()-> new ResourceNotFoundException("Comment", "comentID", commentid));
		
		
		return modelmapper.map(comment, CommentDto.class);
		
	}

	@Override
	public List<CommentDto> getAllComments() {
		
		List<Comment> allComments = comrepo.findAll();
		
		return allComments.stream().map(e-> modelmapper.map(e, CommentDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public CommentDto editComment(Integer commentid,CommentDto comdto) {
		
		Comment comment = comrepo.findById(commentid).orElseThrow(()-> new ResourceNotFoundException("Comment", "comentID", commentid));
		
		comment.setCommentContent(comdto.getCommentContent());
		
		Comment updtedComment = comrepo.save(comment);
		return modelmapper.map(updtedComment, CommentDto.class);
	}

}
