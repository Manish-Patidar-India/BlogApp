package com.manishblogapp.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manishblogapp.entities.Category;
import com.manishblogapp.entities.Posts;
import com.manishblogapp.entities.UserEntity;
import com.manishblogapp.exceptions.ResourceNotFoundException;
import com.manishblogapp.payloads.PostsDto;
import com.manishblogapp.payloads.PostsResponse;
import com.manishblogapp.repositories.CategoryRepository;
import com.manishblogapp.repositories.PostsRepository;
import com.manishblogapp.repositories.UserRepository;
import com.manishblogapp.utils.AppConstant;
import com.manishblogapp.utils.ImageOperation;

@Transactional
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostsRepository postrepo;

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	UserRepository userrepo;

	@Autowired
	CategoryRepository catrepo;

	@Override
	public PostsDto createPost(PostsDto postdto, Integer userid, Integer catid, MultipartFile mfile)
			throws IOException {

		String imgName = "";

		Posts post = modelmapper.map(postdto, Posts.class);

		UserEntity user = userrepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("user", "ID", userid));

		Category cat = catrepo.findById(catid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", catid));

		
		if (mfile != null) {
			var lastindex=mfile.getOriginalFilename().lastIndexOf(".");
			String savedname =  UUID.randomUUID().toString()+mfile.getOriginalFilename().substring(lastindex);
			imgName = ImageOperation.uploadImage(mfile, savedname);
		} else {
			imgName = AppConstant.DEFAULT_IMGNAME;
		}

		post.setUser(user);
		post.setCategory(cat);
		post.setDate(LocalDate.now());
		post.setImage(imgName);

		Posts savedpost = postrepo.save(post);

		return modelmapper.map(savedpost, PostsDto.class);

	}

	@Override
	public PostsDto getPost(Integer postid) {

		Posts post = postrepo.findById(postid).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postid));
		return modelmapper.map(post, PostsDto.class);
	}

	@Override
	public PostsResponse getALlPost(Integer pageno, Integer pagesize, String sortby, String sortdir) {

//		Sort sort=null;
		/*
		 * if (sortdir.equalsIgnoreCase("asc")) {
		 * 
		 * sort = Sort.by(sortby).ascending(); } else {
		 * 
		 * sort = Sort.by(sortby).descending(); }
		 */

		Sort sort = sortdir.equalsIgnoreCase("asc") ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		Page<Posts> pagepost = postrepo.findAll(p);

		PostsResponse postresponse = new PostsResponse();

		List<Posts> posts = pagepost.getContent();

		postresponse
				.setPostdto(posts.stream().map(e -> modelmapper.map(e, PostsDto.class)).collect(Collectors.toList()));

		postresponse.setPageno(pagepost.getNumber());
		postresponse.setPagesize(pagepost.getSize());
		postresponse.setTotalElements(pagepost.getTotalElements());
		postresponse.setLastPage(pagepost.isLast());
		postresponse.setTotalPages(pagepost.getTotalPages());

		return postresponse;
	}

	@Override
	public void deletePost(Integer postid) {

		postrepo.findById(postid).ifPresentOrElse(e -> {
			postrepo.deleteById(postid);
		}, () -> {
			throw new ResourceNotFoundException("Post", "ID", postid);
		});

	}

	@Override
	public PostsDto updatePost(PostsDto postdto, Integer postid, Integer userid, Integer catid) {

		Posts post = postrepo.findById(postid).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postid));
		UserEntity user = userrepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("user", "ID", userid));

		Category cat = catrepo.findById(catid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", catid));

		post.setPostTitle(postdto.getPostTitle());
		post.setContent(postdto.getContent());
		post.setCategory(cat);
		post.setUser(user);

		Posts savedpost = postrepo.save(post);

		return modelmapper.map(savedpost, PostsDto.class);

	}

	@Override
	public PostsResponse getALlPostByUser(Integer userid, Integer pageno, Integer pagesize, String sortby,
			String sortdir) {

		UserEntity user = userrepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("user", "ID", userid));

		Sort sort = sortdir.equalsIgnoreCase("asc") ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		Page<Posts> pagepost = postrepo.findByUser(user, p);

		PostsResponse postresponse = new PostsResponse();

		List<Posts> posts = pagepost.getContent();

		postresponse
				.setPostdto(posts.stream().map(e -> modelmapper.map(e, PostsDto.class)).collect(Collectors.toList()));

		postresponse.setPageno(pagepost.getNumber());
		postresponse.setPagesize(pagepost.getSize());
		postresponse.setTotalElements(pagepost.getTotalElements());
		postresponse.setLastPage(pagepost.isLast());
		postresponse.setTotalPages(pagepost.getTotalPages());

		return postresponse;

	}

	@Override
	public List<PostsDto> getALlPostByCategory(Integer catid) {
		Category cat = catrepo.findById(catid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", catid));
		List<Posts> posts = postrepo.findByCategory(cat);
		return posts.stream().map(e -> modelmapper.map(e, PostsDto.class)).collect(Collectors.toList());

	}

	@Override
	public Integer deleteAllPostsByUser(Integer userid) {

		UserEntity user = userrepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("user", "ID", userid));

		Integer deletecount = postrepo.deleteByUser(user);

		return deletecount;

	}

	@Override
	public List<PostsDto> searchPosts(String keywords) {
		System.out.println(keywords);

		List<Posts> posts = postrepo.findByContentContainingIgnoreCase(keywords);
		/*
		 * List<Posts>post=postrepo.findAll(); List<Posts>
		 * posts=post.stream().filter(e->
		 * (e.getContent().contains(k))).collect(Collectors.toList());
		 */
		return posts.stream().map(e -> modelmapper.map(e, PostsDto.class)).collect(Collectors.toList());

	}

	@Override
	public List<PostsDto> searchPostWithDate(LocalDate date) {

		List<Posts> post = postrepo.findByDate(date);
		return post.stream().map(e -> modelmapper.map(e, PostsDto.class)).collect(Collectors.toList());
	}

	@Override
	public String UpdatePostImage(Integer postid, MultipartFile file) throws IOException {

		Posts post = postrepo.findById(postid).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postid));

		String imgName = null;
       
		var lastindex=file.getOriginalFilename().lastIndexOf(".");
		
		if (file != null) {
			if (post.getImage().equalsIgnoreCase(AppConstant.DEFAULT_IMGNAME)) {
				String savedname = UUID.randomUUID().toString()+file.getOriginalFilename().substring(lastindex);
				imgName = ImageOperation.uploadImage(file, savedname);
			} else {
                
				imgName = ImageOperation.uploadImage(file, post.getImage());
			}
		} 

		post.setImage(imgName);
		postrepo.save(post);
		return imgName;
	}

	@Override
	public void getImage(HttpServletResponse response, String imagename) throws IOException {

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(ImageOperation.downloadImage(imagename), response.getOutputStream());

	}

}
