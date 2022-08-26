package com.manishblogapp.utils;

public class AppConstant {

	
	public static final String DEFAULT_IMGNAME="avatar.jpg";
	
	public static final String POST_IMGFOLDER="src/main/resources/static/images";
	
	
	public static final String DEFAULT_PGSIZE="5";
	
	public static final String DEFAULT_CURRPAGE="0";
	
	public static final String DEFAULT_SORTDIR="asc";
	
	public static final String DEFAULT_SORT_BY="postTitle";
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	
	public static final String[] PUBLIC_URLS = {
			"/auth/**",
			"/user/register/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"

	};
}
