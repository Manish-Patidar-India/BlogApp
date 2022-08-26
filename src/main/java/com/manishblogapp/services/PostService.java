package com.manishblogapp.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.manishblogapp.payloads.PostsDto;
import com.manishblogapp.payloads.PostsResponse;

public interface PostService {

	PostsDto createPost(PostsDto postdto, Integer userid, Integer catid, MultipartFile mfile) throws IOException;

	PostsDto getPost(Integer postid);

	PostsResponse getALlPost(Integer pageno, Integer pagesize, String sortby, String sortdir);

	void deletePost(Integer postid);

	PostsDto updatePost(PostsDto postdto, Integer postid, Integer userid, Integer catid);

	PostsResponse getALlPostByUser(Integer userid, Integer pageno, Integer pagesize, String sortby, String sortdir);

	List<PostsDto> getALlPostByCategory(Integer catid);

	Integer deleteAllPostsByUser(Integer userid);

	List<PostsDto> searchPosts(String cont);

	List<PostsDto> searchPostWithDate(LocalDate date);

	String UpdatePostImage(Integer postid, MultipartFile file) throws IOException;

	void getImage(HttpServletResponse response, String imagename) throws IOException;

}
