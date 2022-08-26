package com.manishblogapp.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	private String postTitle;
	
	@Column(length = 1000)
	private String content;
	
	private String image;
	
	private LocalDate date;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private UserEntity user;
	
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comment;
}
