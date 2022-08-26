package com.manishblogapp.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostsResponse {

	private List<PostsDto> postdto;

	private Integer pageno;

	private Integer pagesize;

	private long totalElements;

	private Integer totalPages;

	private boolean isLastPage;

}
