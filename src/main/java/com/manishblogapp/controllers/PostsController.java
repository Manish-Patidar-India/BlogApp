package com.manishblogapp.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.manishblogapp.payloads.ApiResponse;
import com.manishblogapp.payloads.PostsDto;
import com.manishblogapp.payloads.PostsResponse;
import com.manishblogapp.services.PostService;
import com.manishblogapp.utils.AppConstant;

@RestController
public class PostsController {

	@Autowired
	PostService postService;

	@Autowired
	Environment env;

	@PostMapping("/user/{userid}/cat/{catid}/")
	public ResponseEntity<PostsDto> createPost(@Valid @RequestBody PostsDto postdto, @PathVariable Integer userid,
			@PathVariable Integer catid, @RequestParam(value = "image", required = false) MultipartFile multipart)
			
			throws IOException {
        
		
		PostsDto post = postService.createPost(postdto, userid, catid, multipart);

		return new ResponseEntity<>(post, HttpStatus.CREATED);

	}

	@GetMapping("/post/{postid}")
	public ResponseEntity<PostsDto> getPost(@PathVariable Integer postid) {

		PostsDto post = postService.getPost(postid);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/post/")
	public ResponseEntity<PostsResponse> getAllPosts(
			@RequestParam(value = "pageno", required = false, defaultValue = AppConstant.DEFAULT_CURRPAGE) Integer pageno,
			@RequestParam(value = "pagesize", required = false, defaultValue = AppConstant.DEFAULT_PGSIZE) Integer pagesize,
			@RequestParam(value = "sortby", required = false, defaultValue = AppConstant.DEFAULT_SORT_BY) String sortby,
			@RequestParam(value = "sortdir", required = false, defaultValue = AppConstant.DEFAULT_SORTDIR) String sortdir) {

		PostsResponse aLlPost = postService.getALlPost(pageno, pagesize, sortby, sortdir);

		return new ResponseEntity<>(aLlPost, HttpStatus.OK);
	}

	@DeleteMapping("/post/{postid}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postid) {

		postService.deletePost(postid);

		return new ResponseEntity<>(new ApiResponse("post deleted", "success"), HttpStatus.OK);
	}

	@PutMapping("/user/{userid}/cat/{catid}/post/{postid}")
	public ResponseEntity<PostsDto> updatePost(@Valid @RequestBody PostsDto postdto, @PathVariable Integer userid,
			@PathVariable Integer catid, @PathVariable Integer postid) {

		PostsDto post = postService.updatePost(postdto, postid, userid, catid);

		return new ResponseEntity<>(post, HttpStatus.OK);

	}

	@GetMapping("/post/user/{userid}")
	public ResponseEntity<PostsResponse> getAllPostsByUser(@PathVariable Integer userid,
			@RequestParam(value = "pageno", required = false, defaultValue = AppConstant.DEFAULT_CURRPAGE) Integer pageno,
			@RequestParam(value = "pagesize", required = false, defaultValue = AppConstant.DEFAULT_PGSIZE) Integer pagesize,
			@RequestParam(value = "sortby", required = false, defaultValue = AppConstant.DEFAULT_SORT_BY) String sortby,
			@RequestParam(value = "sortdir", required = false, defaultValue = AppConstant.DEFAULT_SORTDIR) String sortdir) {

		PostsResponse aLlPost = postService.getALlPostByUser(userid, pageno, pagesize, sortby, sortdir);

		return new ResponseEntity<>(aLlPost, HttpStatus.OK);
	}

	@GetMapping("/post/category/{catid}")
	public ResponseEntity<List<PostsDto>> getAllPostsByCategory(@PathVariable Integer catid) {

		List<PostsDto> aLlPostByCategory = postService.getALlPostByCategory(catid);

		return new ResponseEntity<>(aLlPostByCategory, HttpStatus.OK);
	}

	@DeleteMapping("/post/user/{userid}")
	public ResponseEntity<String> deleteAllPostsByUser(@PathVariable Integer userid) {

		Integer count = postService.deleteAllPostsByUser(userid);

		return new ResponseEntity<>(count + " Posts deleted", HttpStatus.OK);
	}

	// for searching post

	@GetMapping("/post/key/{keyword}")
	public ResponseEntity<List<PostsDto>> searchPost(@PathVariable String keyword) {

		List<PostsDto> posts = postService.searchPosts(keyword);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	// searching with date

	@GetMapping("/post/date/")
	public ResponseEntity<List<PostsDto>> searchPostWithDate(
			@RequestParam(value = "date", required = true) String date) {
        
	
		List<PostsDto> posts = postService.searchPostWithDate(LocalDate.parse(date));

		return new ResponseEntity<>(posts, HttpStatus.OK);

	}

	@GetMapping(value = "/post/image/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(@PathVariable String imagename, HttpServletResponse response) throws IOException {

		postService.getImage(response, imagename);

	}

	@PutMapping("/post/{postid}/image/")
	public ResponseEntity<String> updateImage(@PathVariable Integer postid,
			@RequestParam(value = "image", required = true) MultipartFile file) throws IOException {

		String imgename = postService.UpdatePostImage(postid, file);
              String imgurl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/post/image/").path(imgename).toUriString();
		//String imgurl = env.getProperty("blog.post.image") + imgename;
		return new ResponseEntity<>(imgurl, HttpStatus.OK);

	}

}
