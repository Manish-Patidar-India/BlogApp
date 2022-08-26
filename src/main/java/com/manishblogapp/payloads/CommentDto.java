package com.manishblogapp.payloads;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Integer commentID;
	
    @NotEmpty(message = "Comment should not be emtpy")
	private String commentContent;
	
    
	private LocalDate commentDate;
	
	
}
