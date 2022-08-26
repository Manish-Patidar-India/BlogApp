package com.manishblogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manishblogapp.payloads.CommentDto;
import com.manishblogapp.services.CommentService;

@RestController
public class CommentController {

	@Autowired
	CommentService comservice;

	@PostMapping("/post/{postid}/comment/")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comdto, @PathVariable Integer postid) {

		CommentDto comment = comservice.createComment(postid, comdto);

		return new ResponseEntity<>(comment, HttpStatus.CREATED);

	}

	@PutMapping("/comment/{comid}")
	public ResponseEntity<CommentDto> editComment(@RequestBody CommentDto comdto, @PathVariable Integer comid) {

		CommentDto comment = comservice.editComment(comid, comdto);

		return new ResponseEntity<>(comment, HttpStatus.OK);

	}

	@GetMapping("/comment/{comid}")
	public ResponseEntity<CommentDto> getComment(@PathVariable Integer comid) {

		CommentDto comment = comservice.getComment(comid);

		return new ResponseEntity<>(comment, HttpStatus.OK);

	}

	@GetMapping("/comment/")
	public ResponseEntity<List<CommentDto>> getAllComment() {

		List<CommentDto> comment = comservice.getAllComments();

		return new ResponseEntity<>(comment, HttpStatus.OK);

	}

}
