package com.manishblogapp.payloads;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostsDto {

	
    private Integer postId;
	
    @NotEmpty(message="Post Title should not be empty")
	private String postTitle;
    
    @NotEmpty(message="Post Content should not be empty")
	private String content;
	
	private String image;
	
	private LocalDate date;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private List<CommentDto> comment;
	
	
	
}
